package com.rosed.survivalshowdown.instance;

import com.rosed.survivalshowdown.manager.ConfigManager;
import com.rosed.survivalshowdown.manager.InstanceManager;
import com.rosed.survivalshowdown.manager.WorldManager;
import org.bukkit.Location;

public class Lobby {

    private ConfigManager configManager;
    private WorldManager worldManager;

    private int lobbyID;
    private Location lobbyLocation;

    public Lobby(int lobbyID)   {

        configManager = InstanceManager.INSTANCE.getConfigManager();
        worldManager = InstanceManager.INSTANCE.getWorldManager();

        this.lobbyID = lobbyID;
        setLobbyLocation();

    }

    /**
     * set lobby location from config
     */
    private void setLobbyLocation()   {

        lobbyLocation = configManager.getLocation("lobby-location", worldManager.getLobbyWorldName(lobbyID));

    }

}
