package com.rosed.survivalshowdown.listener.itemlistener;

import com.rosed.survivalshowdown.util.ItemUtil;
import jdk.jpackage.internal.Log;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;

public class ExcaliburHit extends ItemUtil implements Listener {

    private HashMap<Player, Long> excaliburCooldownMap;

    public ExcaliburHit()   {

        excaliburCooldownMap = new HashMap<>();

    }

    @EventHandler
    public void onExcaliburHit(EntityDamageByEntityEvent e)   {

        if (!(playerHitPlayerCheck(e.getEntity(), e.getDamager(), e.getCause())))   return;
        Player attacker = (Player) e.getDamager();
        Player defender = (Player) e.getEntity();

        if (!itemCheck(attacker.getInventory().getItemInMainHand(), "survivalShowdown.excalibur"))   return;
        if (checkCooldown(attacker, excaliburCooldownMap, 5000L))   {
            defender.setHealth(defender.getHealth() - 4.0);
            Bukkit.getWorld(defender.getWorld().getName()).playSound(defender.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0f, 1.0f);
            defender.getWorld().spawnParticle(Particle.LAVA, defender.getLocation().add(0, 1,0 ), 10, 0.3, 0.3, 0.3);
            setCooldown(attacker, excaliburCooldownMap);
        }
    }

}
