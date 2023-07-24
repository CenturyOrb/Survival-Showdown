package com.rosed.survivalshowdown.instance;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
public class Craftable {

    private int timesCanCraft;
    private ItemStack item;
    private String localizedName;

    public Craftable(int timesCanCraft, ItemStack item)   {

        this.timesCanCraft = timesCanCraft;
        this.item = item;
        this.localizedName = item.getItemMeta().getLocalizedName();

    }

    public boolean canCraftAmount(int amount)   {

        if (timesCanCraft >= amount)   {
            timesCanCraft -= amount;
            return true;
        } else {
            return false;
        }

    }

}