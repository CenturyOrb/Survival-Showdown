package com.rosed.survivalshowdown.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class FortunateRepairEvent implements Listener {

    @EventHandler
    public void onFortunateRepair(PrepareAnvilEvent e)   {

        AnvilInventory inv = e.getInventory();

        if (inv.getFirstItem() != null )   {
            if (inv.getFirstItem().getItemMeta().getLocalizedName().equals("survivalShowdown.fortunate"))   {
                e.setResult(new ItemStack(Material.AIR));
            }
        }
        if (inv.getSecondItem() != null )   {
            if (inv.getSecondItem().getItemMeta().getLocalizedName().equals("survivalShowdown.fortunate"))   {
                e.setResult(new ItemStack(Material.AIR));
            }
        }

    }
}
