package com.rosed.survivalshowdown.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
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
                        if (!player.getInventory().getItemInMainHand().getItemMeta().hasLocalizedName())   {
                            continue;
                        } else {
                            String itemLocalizedName = player.getInventory().getItemInMainHand().getItemMeta().getLocalizedName();
                            if (!itemLocalizedName.equals("survivalShowdown.anduril"))   continue;
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1, 0, true, false, false));
                        }

                    }
                }
            }, 0, 50);

        }
        

}
