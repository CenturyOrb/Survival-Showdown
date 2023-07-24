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
    private CraftManager craftManager;

    private final int lobbyID;
    private List<Player> playerList;
    private Location lobbyLocation;
    private Game game;

    public Lobby(int lobbyID)   {

        configManager = InstanceManager.INSTANCE.getConfigManager();
        worldManager = InstanceManager.INSTANCE.getWorldManager();
        craftManager = InstanceManager.INSTANCE.getCraftManager();

        this.lobbyID = lobbyID;
        playerList = new ArrayList<>();
        setLobbyLocation();
        game = new Game(lobbyID);

        System.out.println("Lobby: " + lobbyID + " is loaded");
    }

    public void addPlayerToLobby(Player player)   {

        playerList.add(player);
        craftManager.getCraftableMap().put(player, craftManager.getCraftablePreset());
        player.teleport(lobbyLocation);

    }

    public void removePlayerFromLobby(Player player)   {

        playerList.remove(player);
        craftManager.getCraftableMap().remove(player);
        player.teleport(configManager.getLocation("hub", configManager.getConfig().getString("hub.world")));

    }

    /**
     * set lobby location from config
     */
    private void setLobbyLocation()   {

        lobbyLocation = configManager.getLocation("lobby-location", worldManager.getLobbyWorldName(lobbyID));

    }

}
