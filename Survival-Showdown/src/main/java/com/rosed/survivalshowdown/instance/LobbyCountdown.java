package com.rosed.survivalshowdown.instance;

import com.rosed.survivalshowdown.SurvivalShowdown;
import com.rosed.survivalshowdown.manager.ConfigManager;
import com.rosed.survivalshowdown.manager.InstanceManager;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class LobbyCountdown extends BukkitRunnable {

    private SurvivalShowdown survivalShowdown;
    private ConfigManager configManager;

    private final Lobby lobby;
    private final Game game;
    private int lobbyCountdown;

    public LobbyCountdown(Lobby lobby)   {

        survivalShowdown = InstanceManager.INSTANCE.getSurvivalShowdown();
        configManager = InstanceManager.INSTANCE.getConfigManager();

        this.lobby = lobby;
        game = lobby.getGame();
        lobbyCountdown = configManager.getLobbyCountdown();

        System.out.println("Created LobbyCountdown");

    }

    /**
     * starts the lobby's countdown
     */
    public void start()   {

        runTaskTimer(survivalShowdown, 0, 20);

    }

    @Override
    public void run() {

        if (lobbyCountdown == 0)   {
            cancel();
            game.start();
            return;
        }

        if (lobbyCountdown <= 10 || lobbyCountdown % 30 == 0)   {
            lobby.sendMessage(ChatColor.GREEN + "Game will start in " + lobbyCountdown + " second" + (lobbyCountdown > 1 ? "s!" : "!"));
        }

        lobbyCountdown--;

    }

}
