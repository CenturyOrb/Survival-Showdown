package com.rosed.survivalshowdown.util;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

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

    protected boolean playerHitPlayerCheck (Entity defender, Entity attacker, EntityDamageEvent.DamageCause damageCause)  {
        boolean playerAttackedPlayer = true;

        // check if the deffending entity is not living
        if (!(defender instanceof Player))  {
            playerAttackedPlayer = false;
        }

        // check if attacker isn't a player
        if (!(attacker instanceof Player))  {
            playerAttackedPlayer = false;
        }

        // check if cause is thorns
        if (damageCause == EntityDamageEvent.DamageCause.THORNS) {
            playerAttackedPlayer = false;
        }

        return playerAttackedPlayer;
    }

    protected boolean checkCooldown(Player player, HashMap<Player, Long> cooldowns, Long cooldown) {

        if (!cooldowns.containsKey(player) || cooldowns.get(player) + cooldown <= System.currentTimeMillis()) {
            return true;
        }
        return false;

    }

    protected void setCooldown(Player player, HashMap<Player, Long> cooldowns) {
        cooldowns.put(player, System.currentTimeMillis());
    }

}
