package com.rosed.survivalshowdown.instance;

import com.rosed.survivalshowdown.SurvivalShowdown;
import com.rosed.survivalshowdown.manager.InstanceManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

@Getter
public class GameScoreboard {

    private final SurvivalShowdown survivalShowdown;

    private Game game;
    private Scoreboard board;
    private int minutes;

    public GameScoreboard(Game game)   {

        survivalShowdown = InstanceManager.INSTANCE.getSurvivalShowdown();

        this.game = game;
        minutes = 30;

        board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("liveboard", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(ChatColor.YELLOW + ChatColor.BOLD.toString() + "    SURVIVAL SHOWDOWN    ");

        Score emptyLine1 = obj.getScore("");
        emptyLine1.setScore(5);

        Score time = obj.getScore(ChatColor.BOLD + ChatColor.GOLD.toString() + "Arena Enables in");
        time.setScore(4);

        Team timeCountdown = board.registerNewTeam("time");
        timeCountdown.addEntry(ChatColor.BOLD.toString());
        timeCountdown.setPrefix("" + minutes);
        timeCountdown.setSuffix(" Minutes");
        obj.getScore(ChatColor.BOLD.toString()).setScore(3);

        Score emptyLine2 = obj.getScore(" ");
        emptyLine2.setScore(2);

        Score serverIP = obj.getScore(ChatColor.YELLOW + "localhost");
        serverIP.setScore(1);

        new BukkitRunnable() {
            @Override
            public void run() {
                updateTimer();
            }
        }.runTaskTimer(survivalShowdown, 1200, 60 * 20);

    }

    public void updateTimer()   {

        minutes--;
        game.getPlayerList().forEach(player -> player.getScoreboard().getTeam("time").setPrefix("" + minutes));

    }

}
