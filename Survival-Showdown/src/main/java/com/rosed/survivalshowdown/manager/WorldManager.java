package com.rosed.survivalshowdown.manager;

import com.onarandombox.MultiverseCore.api.MVWorldManager;

public class WorldManager {

    private ConfigManager configManager;
    private MVWorldManager mvWorldManager;
    private LobbyManager lobbyManager;

    public WorldManager()   {

        configManager = InstanceManager.INSTANCE.getConfigManager();
        mvWorldManager = InstanceManager.INSTANCE.getMvWorldManager();
        lobbyManager = InstanceManager.INSTANCE.getLobbyManager();

        // clone the worlds
        cloneLobbyWorld(configManager.getNumLobby());

    }

    private void cloneLobbyWorld(int lobbyNum)   {

        for (int i = 0; i < lobbyNum; i++)   {
            mvWorldManager.cloneWorld(configManager.getLobbyExampleName(), getLobbyName(i));
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

}
