package com.rosed.survivalshowdown.instance;

import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import com.rosed.survivalshowdown.SurvivalShowdown;
import com.rosed.survivalshowdown.manager.ConfigManager;
import com.rosed.survivalshowdown.manager.InstanceManager;
import com.rosed.survivalshowdown.manager.WorldManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Game extends BukkitRunnable {

    private final SurvivalShowdown survivalShowdown;
    private final ConfigManager configManager;
    private final WorldManager worldManager;

    private final Lobby lobby;
    private final int gameID;
    private GameState gameState;
    private List<Player> playerList;
    private Location player1ArenaLocation, player2ArenaLocation;

    public Game(Lobby lobby)   {

        survivalShowdown = InstanceManager.INSTANCE.getSurvivalShowdown();
        configManager = InstanceManager.INSTANCE.getConfigManager();
        worldManager = InstanceManager.INSTANCE.getWorldManager();

        this.lobby = lobby;
        this.gameID = lobby.getLobbyID();
        gameState = GameState.RECRUITING;
        playerList = new ArrayList<>();

        setPlayerArenaLocations();

        System.out.println("Game: " + gameID + " is loaded");

    }

    /**
     * start the game, teleport players to the live worlds
     */
    public void start()   {

        playerList = lobby.getPlayerList();

        // teleport players to rightful worlds
        for (Player player : playerList)   {
            if (worldManager.getLivePlayerWorldMap().get(player) != null)   {
                MultiverseWorld mvWorld = worldManager.getLivePlayerWorldMap().get(player).get(0);
                player.teleport(mvWorld.getSpawnLocation());
            }
        }

        gameState = GameState.LIVE;
        lobby.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l-----------------------------------------------"));
        lobby.sendMessage(ChatColor.translateAlternateColorCodes('&', "&l                     Survival Showdown         "));
        lobby.sendMessage("");
        lobby.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e                  Gather resources to prepare  "));
        lobby.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e                  for the fight. First to 3 wins"));
        lobby.sendMessage("");
        lobby.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l-----------------------------------------------"));

        // start the timer for Arena Fight
        runTaskLater(survivalShowdown, 400);
    }

    @Override
    public void run() {

        gameState = GameState.ARENA;
        playerList.get(0).teleport(player1ArenaLocation);
        playerList.get(1).teleport(player2ArenaLocation);
        lobby.sendTitle(ChatColor.RED + "FINAL FIGHT", "");

    }

    /**
     * ends the game
     */
    public void end()   {

        // remove players from lobby playerlist
        // delete live worlds
        // reset Arena world
        // teleport players to Hub

    }

    /**
     * set player location to arena from config
     */
    private void setPlayerArenaLocations()   {

        player1ArenaLocation = configManager.getLocation("arena-location.player1", worldManager.getArenaWorldName(gameID));
        player2ArenaLocation = configManager.getLocation("arena-location.player2", worldManager.getArenaWorldName(gameID));

    }

    /**
     * sets game state
     * @param gameState GameState
     */
    public void setGameState(GameState gameState)   { this.gameState = gameState; }

    public void setPlayerList(List<Player> playerList)   {

        this.playerList = playerList;

    }

}
