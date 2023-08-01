package com.rosed.survivalshowdown.instance;

import com.rosed.survivalshowdown.SurvivalShowdown;
import com.rosed.survivalshowdown.manager.InstanceManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.*;

@Getter
public class GameScoreboard {

    private final SurvivalShowdown survivalShowdown;

    private final Game game;
    private final Scoreboard board;
    private int minutes = 30;
    private final BukkitTask countdown;

    public GameScoreboard(Game game)   {

        survivalShowdown = InstanceManager.INSTANCE.getSurvivalShowdown();

        this.game = game;

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
        timeCountdown.setSuffix(" minutes");
        obj.getScore(ChatColor.BOLD.toString()).setScore(3);

        Score emptyLine2 = obj.getScore(" ");
        emptyLine2.setScore(2);

        Score serverIP = obj.getScore(ChatColor.YELLOW + "localhost");
        serverIP.setScore(1);

        countdown = new BukkitRunnable() {
            @Override
            public void run() {
                updateTimer();
            }
        }.runTaskTimer(survivalShowdown, 1200, 60 * 20);

    }

    /**
     * keeps the timer on sidebar updated
     */
    public void updateTimer()   {

        minutes--;
        game.getPlayerList().forEach(player -> player.getScoreboard().getTeam("time").setPrefix("" + minutes));
        if (minutes == 1)   {
            game.getPlayerList().forEach(player -> player.getScoreboard().getTeam("time").setSuffix(" minute"));
        } else if (minutes == 0){
            countdown.cancel();
        }

    }

}
