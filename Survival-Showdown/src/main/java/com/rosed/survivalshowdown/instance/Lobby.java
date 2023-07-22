package com.rosed.survivalshowdown.instance;

import com.rosed.survivalshowdown.manager.ConfigManager;
import com.rosed.survivalshowdown.manager.InstanceManager;
import com.rosed.survivalshowdown.manager.WorldManager;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Lobby {

    private final ConfigManager configManager;
    private final WorldManager worldManager;

    private final int lobbyID;
    private List<Player> playerList;
    private Location lobbyLocation;

    public Lobby(int lobbyID)   {

        configManager = InstanceManager.INSTANCE.getConfigManager();
        worldManager = InstanceManager.INSTANCE.getWorldManager();

        this.lobbyID = lobbyID;
        playerList = new ArrayList<>();
        setLobbyLocation();

        System.out.println("Lobby: " + lobbyID + " is loaded");
    }

    /**
     * set lobby location from config
     */
    private void setLobbyLocation()   {

        lobbyLocation = configManager.getLocation("lobby-location", worldManager.getLobbyWorldName(lobbyID));

    }

}
