package com.rosed.survivalshowdown.listener.itemlistener;

import com.rosed.survivalshowdown.util.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AndurilHold extends ItemUtil {

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

}
