package com.rosed.survivalshowdown.listener.itemlistener;

import com.rosed.survivalshowdown.util.ItemUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class ExodusWear extends ItemUtil implements Listener {

    private HashMap<Player, Long> exodusCooldownMap;
    private final long exodusCooldown = 2500;

    public ExodusWear()   {

        exodusCooldownMap = new HashMap<>();

    }

    @EventHandler
    public void onExodusHit(EntityDamageByEntityEvent e)   {

        if (!(playerHitPlayerCheck(e.getEntity(), e.getDamager(), e.getCause())))   return;
        Player attacker = (Player) e.getDamager();
        if (checkCooldown(attacker, exodusCooldownMap, exodusCooldown) &&
        itemCheck(attacker.getInventory().getHelmet(), "survivalShowdown.exodus"))   {
            attacker.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40, 0, true, false, false));
            setCooldown(attacker, exodusCooldownMap);
        }

    }

}
