package com.rosed.survivalshowdown.command;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WorldInfoCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player))   { return false; }
        Player player = (Player) sender;

        List<World> worlds = Bukkit.getServer().getWorlds();

        for (World world : worlds)   {

            if (world.getPlayers().contains(player))   {

                player.sendMessage("You are in " + world.getName());

            }

        }

        for (World world : worlds)   {

            player.sendMessage(world.getName());

        }

        return false;
    }
}
