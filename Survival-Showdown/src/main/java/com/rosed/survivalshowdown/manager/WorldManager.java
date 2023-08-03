package com.rosed.survivalshowdown.manager;

import com.onarandombox.MultiverseCore.MVWorld;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import com.rosed.survivalshowdown.instance.Lobby;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Getter
public class WorldManager {

    private final ConfigManager configManager;
    private final MVWorldManager mvWorldManager;

    private HashMap<Player, List<MultiverseWorld>> livePlayerWorldMap;

    public WorldManager()   {

        configManager = InstanceManager.INSTANCE.getConfigManager();
        mvWorldManager = InstanceManager.INSTANCE.getMvWorldManager();

        livePlayerWorldMap = new HashMap<>();

        // clone the worlds
        loadExampleWorlds();
        cloneLobbyWorlds(configManager.getNumLobby());
        cloneArenaWorlds(configManager.getNumLobby());

    }

    /**
     * loads example worlds
     */
    private void loadExampleWorlds()   {

        if (!mvWorldManager.isMVWorld(Bukkit.getWorld(configManager.getLobbyExampleName())))   {
            mvWorldManager.addWorld(configManager.getLobbyExampleName(), World.Environment.valueOf("NORMAL"), null, null, null, "NORMAL", false);
        }

        if (!mvWorldManager.isMVWorld(Bukkit.getWorld(configManager.getArenaExampleName())))   {
            mvWorldManager.addWorld(configManager.getArenaExampleName(), World.Environment.valueOf("NORMAL"), null, null, null, "NORMAL", false);
        }

    }

    /**
     * unloads example lobby and arena worlds
     */
    public void unloadWorlds()   {

        mvWorldManager.unloadWorld(configManager.getLobbyExampleName());
        mvWorldManager.unloadWorld(configManager.getArenaExampleName());

    }

    public void unloadDefaultWorlds()   {

        mvWorldManager.unloadWorld("Hub_nether");
        mvWorldManager.unloadWorld("Hub_the_end");

    }

    /**
     * deletes arena and lobby copy worlds
     */
    public void deleteCopyWorlds()   {

        for (MultiverseWorld mvWorld : mvWorldManager.getMVWorlds())   {
            String worldName = mvWorld.getName();

            if (worldName.contains("Copy"))   {
                mvWorldManager.deleteWorld(worldName);
            }
        }

    }

    /**
     * deletes all live worlds
     */
    public void deleteLiveWorlds()   {

        for (MultiverseWorld mvWorld : mvWorldManager.getMVWorlds())   {
            String worldName = mvWorld.getName();

            String overworldFormat = configManager.getLiveOverworldFormat().replace("#", "");
            String netherFormat = configManager.getLiveNetherFormat().replace("#", "");
            String endFormat = configManager.getLiveEndFormat().replace("#", "");

            if (worldName.contains(overworldFormat) ||
                worldName.contains(netherFormat) ||
                worldName.contains(endFormat))   {
                mvWorldManager.deleteWorld(worldName, true, true);
            }
        }

    }

    /**
     * deletes overworld, nether and end for a game
     */
    public void deleteGameWorlds(Player player)   {

        List<MultiverseWorld> mvWorldList = livePlayerWorldMap.get(player);
        mvWorldList.forEach(multiverseWorld -> mvWorldManager.deleteWorld(multiverseWorld.getName(), true, true));

    }

    /**
     * clones lobby worlds with name from config
     * @param lobbyNum number of lobby worlds
     */
    private void cloneLobbyWorlds(int lobbyNum)   {

        for (int i = 0; i < lobbyNum; i++)   {
            mvWorldManager.cloneWorld(configManager.getLobbyExampleName(), getLobbyWorldName(i));
        }
        mvWorldManager.unloadWorld(configManager.getLobbyExampleName());

    }

    /**
     * clones arena worlds with name from config
     * @param lobbyNum number of arena worlds
     */
    private void cloneArenaWorlds(int lobbyNum)   {

        for (int i = 0; i < lobbyNum; i++)   {
            mvWorldManager.cloneWorld(configManager.getArenaExampleName(), getArenaWorldName(i));
            Bukkit.getWorld(getArenaWorldName(i)).setGameRuleValue("keepInventory", "true");
            Bukkit.getWorld(getArenaWorldName(i)).setGameRuleValue("doImmediateRespawn", "true");
        }
        mvWorldManager.unloadWorld(configManager.getArenaExampleName());

    }

    public void resetArenaWorld(int lobbyID)   {

        Bukkit.broadcastMessage("resetarenaworld ran");
        Bukkit.broadcastMessage(mvWorldManager.deleteWorld(getArenaWorldName(lobbyID), true, true) + " to deletign");

        Bukkit.broadcastMessage(mvWorldManager.loadWorld(configManager.getArenaExampleName()) + " to loading");

        Bukkit.broadcastMessage(mvWorldManager.cloneWorld(configManager.getArenaExampleName(), getArenaWorldName(lobbyID)) + " to cloning");
        Bukkit.getWorld(getArenaWorldName(lobbyID)).setGameRuleValue("keepInventory", "true");
        Bukkit.getWorld(getArenaWorldName(lobbyID)).setGameRuleValue("doImmediateRespawn", "true");

        Bukkit.broadcastMessage(mvWorldManager.unloadWorld(configManager.getArenaExampleName()) + " to unloading");

    }

    /**
     * creates overworld, nether, end worlds for game
     * @param lobbyID lobby id
     */
    public void createLiveWorlds(Lobby lobby, int lobbyID)   {

        ThreadLocalRandom random = ThreadLocalRandom.current();
        long number = random.nextLong();
        String stringSeed = number + "";
        String p1End = "1_" + lobbyID;
        String p2End = "2_" + lobbyID;

        Player p1 = lobby.getPlayerList().get(0);
        Player p2 = lobby.getPlayerList().get(1);

        createPlayerLiveWorlds(p1, stringSeed, p1End);
        createPlayerLiveWorlds(p2, stringSeed, p2End);

    }

    private void createPlayerLiveWorlds(Player player, String stringSeed, String playerEnd) {

        List<MultiverseWorld> playerMVLiveWorld = new ArrayList<>();

        mvWorldManager.addWorld(configManager.getLiveOverworldFormat().replace("#", playerEnd), World.Environment.NORMAL, stringSeed, WorldType.NORMAL, true, "Multiverse-Core");
        playerMVLiveWorld.add(mvWorldManager.getMVWorld(configManager.getLiveOverworldFormat().replace("#", playerEnd)));

        mvWorldManager.addWorld(configManager.getLiveNetherFormat().replace("#", playerEnd) + "_nether", World.Environment.NETHER, stringSeed, WorldType.NORMAL, true, "Multiverse-Core");
        playerMVLiveWorld.add(mvWorldManager.getMVWorld(configManager.getLiveNetherFormat().replace("#", playerEnd) + "_nether"));

        mvWorldManager.addWorld(configManager.getLiveEndFormat().replace("#", playerEnd) + "_the_end", World.Environment.THE_END, stringSeed, WorldType.NORMAL, true, "Multiverse-Core");
        playerMVLiveWorld.add(mvWorldManager.getMVWorld(configManager.getLiveEndFormat().replace("#", playerEnd) + "_the_end"));

        livePlayerWorldMap.put(player, playerMVLiveWorld);
    }

    /**
     * get lobby world name including the lobby ID
     * @param lobbyID lobby ID
     * @return return lobby world name
     */
    public String getLobbyWorldName(int lobbyID)   {

        return configManager.getLobbyFormat().replace("#", String.valueOf(lobbyID));

    }

    /**
     * get arena world name including the ID
     * @param lobbyID lobby ID
     * @return return arena world name
     */
    public String getArenaWorldName(int lobbyID)   {

        return configManager.getArenaFormat().replace("#", String.valueOf(lobbyID));

    }

}
