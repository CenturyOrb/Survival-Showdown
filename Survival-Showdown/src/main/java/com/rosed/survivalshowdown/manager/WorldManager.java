package com.rosed.survivalshowdown.manager;

import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class WorldManager {

    private ConfigManager configManager;
    private MVWorldManager mvWorldManager;

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

        Object lobbyExampleLoaded = mvWorldManager.isMVWorld(Bukkit.getWorld(configManager.getLobbyExampleName())) ? null : mvWorldManager.addWorld(configManager.getLobbyExampleName(), World.Environment.valueOf("NORMAL"), null, null, null, "NORMAL", false);
        Object arenaExampleLoaded = mvWorldManager.isMVWorld(Bukkit.getWorld(configManager.getArenaExampleName())) ? null : mvWorldManager.addWorld(configManager.getArenaExampleName(), World.Environment.valueOf("NORMAL"), null, null, null, "NORMAL", false);

    }

    /**
     * should unload all worlds
     */
    public void unloadWorlds()   {

        mvWorldManager.unloadWorld(configManager.getLobbyExampleName());
        mvWorldManager.unloadWorld(configManager.getArenaExampleName());

    }

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
            mvWorldManager.cloneWorld(configManager.getLobbyExampleName(), getLobbyName(i));
        }

        System.out.println("Finished cloneLobbyWorld()");
        System.out.println("Bukkit#getWorlds(): " + Bukkit.getServer().getWorlds());
        System.out.println("MultiCore#getWorlds(): " + mvWorldManager.getMVWorlds());

    }

    private void cloneArenaWorlds(int lobbyNum)   {

        for (int i = 0; i < lobbyNum; i++)   {
            mvWorldManager.cloneWorld(configManager.getArenaExampleName(), getArenaName(i));
        }

    }

    /**
     * get lobby world name including the lobby ID
     * @param lobbyID lobby ID
     * @return return lobby world name
     */
    public String getLobbyName(int lobbyID)   {

        return configManager.getLobbyFormat().replace("#", String.valueOf(lobbyID));

    }

    /**
     * get arena world name including the ID
     * @param lobbyID lobby ID
     * @return return arena world name
     */
    public String getArenaName(int lobbyID)   {

        return configManager.getArenaFormat().replace("#", String.valueOf(lobbyID));

    }

}
