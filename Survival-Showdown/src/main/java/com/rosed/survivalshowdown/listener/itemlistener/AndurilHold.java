package com.rosed.survivalshowdown.listener.itemlistener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AndurilHold {

    JavaPlugin plugin;

    public AndurilHold(JavaPlugin plugin)   {

        this.plugin = plugin;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers())   {
                    PlayerInventory inventory = player.getInventory();
                    if (itemCheck(inventory.getItemInMainHand(), "survivalShowdown.anduril"))   {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 0, true, false, false));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 80, 0, true, false, false));
                    } else if (itemCheck(inventory.getItemInOffHand(), "survivalShowdown.anduril"))   {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 0, true, false, false));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 80, 0, true, false, false));
                    }
                }
            }
        }, 0, 20);

    }

    /**
     * item is the correct item
     * @param item item to check
     * @param localizedNameChecker correct localized name
     */
    private boolean itemCheck(ItemStack item, String localizedNameChecker)   {

        if (item == null)   return false;
        if (item.getItemMeta() == null)   return false;
        if (!item.getItemMeta().hasLocalizedName())   return false;
        String localizedName = item.getItemMeta().getLocalizedName();

        return localizedName.equals(localizedNameChecker);

    }

}
