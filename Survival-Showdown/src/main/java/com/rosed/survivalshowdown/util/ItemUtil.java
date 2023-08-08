package com.rosed.survivalshowdown.util;

import org.bukkit.inventory.ItemStack;

public abstract class ItemUtil {

    /**
     * item is the correct item
     * @param item item to check
     * @param localizedNameChecker correct localized name
     */
    protected boolean itemCheck(ItemStack item, String localizedNameChecker)   {

        if (item == null)   return false;
        if (item.getItemMeta() == null)   return false;
        if (!item.getItemMeta().hasLocalizedName())   return false;
        String localizedName = item.getItemMeta().getLocalizedName();

        return localizedName.equals(localizedNameChecker);

    }

}
