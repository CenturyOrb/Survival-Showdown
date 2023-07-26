package com.rosed.survivalshowdown.instance;

import com.rosed.survivalshowdown.manager.ConfigManager;
import com.rosed.survivalshowdown.manager.CraftManager;
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
    private final CraftManager craftManager;

    private final int lobbyID;
    private List<Player> playerList;
    private Location lobbyLocation;
    private Game game;
    private LobbyCountdown lobbyCountdown;


    public Lobby(int lobbyID)   {

        configManager = InstanceManager.INSTANCE.getConfigManager();
        worldManager = InstanceManager.INSTANCE.getWorldManager();
        craftManager = InstanceManager.INSTANCE.getCraftManager();

        this.lobbyID = lobbyID;
        playerList = new ArrayList<>();
        setLobbyLocation();
        game = new Game(this);
        this.lobbyCountdown = new LobbyCountdown(this);

        System.out.println("Lobby: " + lobbyID + " is loaded");

    }

    /* PLAYER MANAGEMENT */

    /**
     * called when a player is added to a lobby
     * @param player player that is added
     */
    public void addPlayerToLobby(Player player)   {

        playerList.add(player);
        craftManager.getCraftableMap().put(player, craftManager.getCraftablePreset());
        player.teleport(lobbyLocation);

        // check if there's enough players in the lobby
        if (playerList.size() == 2)   {
            start();
        }

    }

    /**
     * called when a player is removed from a lobby
     * @param player player that is removed
     */
    public void removePlayerFromLobby(Player player)   {

        playerList.remove(player);
        craftManager.getCraftableMap().remove(player);
        player.teleport(configManager.getLocation("hub", configManager.getConfig().getString("hub.world")));

    }

    /* TOOLS */

    /**
     * sends a message to all players of a lobby
     * @param message message
     */
    public void sendMessage(String message)   {

        for (Player player : playerList)   {
            player.sendMessage(message);
        }

    }

    /**
     * sends title to all players of a lobby
     * @param title title
     * @param subtitle subtitle
     */
    public void sendTitle(String title, String subtitle)   {

        for (Player player : playerList)   {
            player.sendTitle(title, subtitle);
        }

    }

    /* LOBBY SETUP */

    public void start()   {

        worldManager.createLiveWorlds(lobbyID);
        lobbyCountdown.start();
        game.setGameState(GameState.COUNTDOWN);

    }

    public void reset(boolean kickPlayers)   {

        if (kickPlayers)   {
            Location hub = configManager.getLocation("hub", configManager.getConfig().getString("hub.world"));
            for (Player player : playerList)   {
                player.teleport(hub);
            }
        }

        game.setGameState(GameState.RECRUITING);
        lobbyCountdown.cancel();
        lobbyCountdown = new LobbyCountdown(this);
        game = new Game(this);

    }

    /**
     * set lobby location from config
     */
    private void setLobbyLocation()   {

        lobbyLocation = configManager.getLocation("lobby-location", worldManager.getLobbyWorldName(lobbyID));

    }

}
