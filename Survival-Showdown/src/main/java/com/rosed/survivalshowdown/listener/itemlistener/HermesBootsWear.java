package com.rosed.survivalshowdown.listener.itemlistener;

import com.rosed.survivalshowdown.SurvivalShowdown;
import com.rosed.survivalshowdown.manager.InstanceManager;
import com.rosed.survivalshowdown.util.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HermesBootsWear extends ItemUtil {

    private SurvivalShowdown survivalShowdown;

    public HermesBootsWear()   {

        survivalShowdown = InstanceManager.INSTANCE.getSurvivalShowdown();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(survivalShowdown, new Runnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers())   {
                    PlayerInventory inventory = player.getInventory();
                    if (itemCheck(inventory.getBoots(), "survivalShowdown.hermesBoots"))   {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 0, true, false, false));
                    }
                }
            }
        }, 0, 20);

    }
}
