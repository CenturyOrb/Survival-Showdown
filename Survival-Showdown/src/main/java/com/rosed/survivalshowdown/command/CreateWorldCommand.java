package com.rosed.survivalshowdown.command;

import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.rosed.survivalshowdown.manager.InstanceManager;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CreateWorldCommand implements CommandExecutor {

    private MVWorldManager mvWorldManager;

    public CreateWorldCommand() {

        mvWorldManager = InstanceManager.INSTANCE.getMvWorldManager();

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player))   { return false; }
        Player player = (Player) sender;

        ThreadLocalRandom random = ThreadLocalRandom.current();
        long number = random.nextLong();
        String stringSeed = number + "";

        mvWorldManager.addWorld("testWorld", World.Environment.NORMAL, stringSeed, WorldType.NORMAL, true, "NORMAL");
        player.sendMessage(ChatColor.GREEN + "Created new world");

        return false;
    }
}
