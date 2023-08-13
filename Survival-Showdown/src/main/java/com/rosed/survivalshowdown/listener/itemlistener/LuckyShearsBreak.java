package com.rosed.survivalshowdown.listener.itemlistener;

import com.rosed.survivalshowdown.util.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class LuckyShearsBreak extends ItemUtil implements Listener {

    @EventHandler
    public void onLuckyShears(BlockBreakEvent e)   {

        if (!e.getBlock().getType().toString().contains("LEAVES"))   return;
        Player player = e.getPlayer();
        Block block = e.getBlock();

        if (!itemCheck(player.getInventory().getItemInMainHand(), "survivalShowdown.luckyShears"))   return;
        if (Math.random() > 0.89)
            player.getWorld().dropItem(block.getLocation(), new ItemStack(Material.APPLE));

    }

}
