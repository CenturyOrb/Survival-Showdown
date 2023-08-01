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

public class PlayerDeathInArenaEvent implements Listener {

    private GameManager gameManager;

    public PlayerDeathInArenaEvent()   {

        gameManager = InstanceManager.INSTANCE.getGameManager();

    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e)   {

        Player player = e.getPlayer();
        Bukkit.broadcastMessage("hi someone died");
        Bukkit.broadcastMessage(gameManager.getPlayerGame(player).toString());
        if (gameManager.getPlayerGame(player) != null)   {
            player.setGameMode(GameMode.SPECTATOR);
            Bukkit.broadcastMessage("hi u died");
            Game game = gameManager.getPlayerGame(player);
            game.nextRound(player);
            player.setGameMode(GameMode.SURVIVAL);
        }

    }

}
