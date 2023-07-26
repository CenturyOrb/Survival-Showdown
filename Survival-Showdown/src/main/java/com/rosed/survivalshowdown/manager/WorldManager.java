package com.rosed.survivalshowdown.manager;

import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldType;

import java.util.concurrent.ThreadLocalRandom;

public class WorldManager {

    private final ConfigManager configManager;
    private final MVWorldManager mvWorldManager;

    public WorldManager()   {

        configManager = InstanceManager.INSTANCE.getConfigManager();
        mvWorldManager = InstanceManager.INSTANCE.getMvWorldManager();

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
        }
        mvWorldManager.unloadWorld(configManager.getArenaExampleName());

    }

    public void createLiveWorlds(int lobbyID)   {

        ThreadLocalRandom random = ThreadLocalRandom.current();
        long number = random.nextLong();
        String stringSeed = number + "";

        mvWorldManager.addWorld("World_Overworld_" +  lobbyID , World.Environment.NORMAL, stringSeed, WorldType.NORMAL, true, "Multiverse-Core");
        Bukkit.broadcastMessage(ChatColor.GREEN + "Created Overworld");

        mvWorldManager.addWorld("World_Nether_" +  lobbyID , World.Environment.NETHER, stringSeed, WorldType.NORMAL, true, "Multiverse-Core");
        Bukkit.broadcastMessage(ChatColor.GREEN + "Created NETHER");

        mvWorldManager.addWorld("World_End_" +  lobbyID , World.Environment.THE_END, stringSeed, WorldType.NORMAL, true, "Multiverse-Core");
        Bukkit.broadcastMessage(ChatColor.GREEN + "Created END");

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
