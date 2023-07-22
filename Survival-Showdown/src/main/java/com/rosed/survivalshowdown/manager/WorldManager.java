package com.rosed.survivalshowdown.manager;

import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class WorldManager {

    private final ConfigManager configManager;
    private final MVWorldManager mvWorldManager;

    public WorldManager()   {

        configManager = InstanceManager.INSTANCE.getConfigManager();
        mvWorldManager = InstanceManager.INSTANCE.getMvWorldManager();

        System.out.println("Finished initializing managers in WorldManager");

        // clone the worlds
        System.out.println("test");
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

    /**
     * clones lobby worlds with name from config
     * @param lobbyNum number of lobby worlds
     */
    private void cloneLobbyWorlds(int lobbyNum)   {

        for (int i = 0; i < lobbyNum; i++)   {
            mvWorldManager.cloneWorld(configManager.getLobbyExampleName(), getLobbyWorldName(i));
        }

        System.out.println("Finished cloneLobbyWorld()");
        System.out.println("Bukkit#getWorlds(): " + Bukkit.getServer().getWorlds());
        System.out.println("MultiCore#getWorlds(): " + mvWorldManager.getMVWorlds());

    }

    /**
     * clones arena worlds with name from config
     * @param lobbyNum number of arena worlds
     */
    private void cloneArenaWorlds(int lobbyNum)   {

        for (int i = 0; i < lobbyNum; i++)   {
            mvWorldManager.cloneWorld(configManager.getArenaExampleName(), getArenaWorldName(i));
        }

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
