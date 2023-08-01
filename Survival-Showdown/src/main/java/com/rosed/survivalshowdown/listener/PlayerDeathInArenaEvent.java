package com.rosed.survivalshowdown.listener;

import com.rosed.survivalshowdown.instance.Game;
import com.rosed.survivalshowdown.manager.GameManager;
import com.rosed.survivalshowdown.manager.InstanceManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerDeathInArenaEvent implements Listener {

    private GameManager gameManager;

    public PlayerDeathInArenaEvent()   {

        gameManager = InstanceManager.INSTANCE.getGameManager();

    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e)   {

        Player player = e.getPlayer();
        if (gameManager.getPlayerGame(player) != null)   {
            Game game = gameManager.getPlayerGame(player);
            game.nextRound(player);
        }

    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e)   {

        Player player = e.getPlayer();
        if (gameManager.getPlayerGame(player) != null)   {
            Game game = gameManager.getPlayerGame(player);
            if (game.getPlayerList().get(0) == player)   {
                e.setRespawnLocation(game.getPlayer1ArenaLocation());
            } else {
                e.setRespawnLocation(game.getPlayer2ArenaLocation());
            }
        }

    }

}
