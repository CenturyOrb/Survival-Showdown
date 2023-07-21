package com.rosed.survivalshowdown.manager;

import com.onarandombox.MultiverseCore.api.MVWorldManager;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;

public class WorldManager {

    private ConfigManager configManager;
    private MVWorldManager mvWorldManager;

    public WorldManager()   {

        configManager = InstanceManager.INSTANCE.getConfigManager();
        mvWorldManager = InstanceManager.INSTANCE.getMvWorldManager();

        System.out.println("Finished initializing managers in WorldManager");

        // clone the worlds
        System.out.println("test");
        cloneLobbyWorld(configManager.getNumLobby());


    }

    private void cloneLobbyWorld(int lobbyNum)   {

        for (int i = 0; i < lobbyNum; i++)   {
            mvWorldManager.cloneWorld(configManager.getLobbyExampleName(), getLobbyName(i));
        }

        System.out.println("Finished cloneLobbyWorld()");
        System.out.println("Bukkit#getWorlds(): " + Bukkit.getServer().getWorlds());
        System.out.println("MultiCore#getWorlds(): " + mvWorldManager.getMVWorlds());

    }

    /**
     * get lobby world name including the lobby ID
     * @param lobbyID lobby ID
     * @return return lobby world name
     */
    public String getLobbyName(int lobbyID)   {

        return configManager.getLobbyFormat().replace("#", String.valueOf(lobbyID));

    }

}
