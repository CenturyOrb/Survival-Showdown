package com.rosed.survivalshowdown.instance;

import com.rosed.survivalshowdown.manager.ConfigManager;
import com.rosed.survivalshowdown.manager.InstanceManager;
import com.rosed.survivalshowdown.manager.WorldManager;
import org.bukkit.Location;

public class Game {

    private ConfigManager configManager;
    private WorldManager worldManager;

    private int gameID;
    private GameState gameState;
    private Location player1ArenaLocation, player2ArenaLocation;

    public Game(int gameID)   {

        configManager = InstanceManager.INSTANCE.getConfigManager();
        worldManager = InstanceManager.INSTANCE.getWorldManager();

        this.gameID = gameID;
        gameState = GameState.RECRUITING;

        setPlayerArenaLocations();

    }

    /**
     * set player location to arena from config
     */
    private void setPlayerArenaLocations()   {

        player1ArenaLocation = configManager.getLocation("arena-location.player1", worldManager.getArenaWorldName(gameID));
        player2ArenaLocation = configManager.getLocation("arena-location.player2", worldManager.getArenaWorldName(gameID));

    }

}
