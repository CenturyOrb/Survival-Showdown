package com.rosed.survivalshowdown.listener.itemlistener;

import com.rosed.survivalshowdown.util.ItemUtil;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DeathsScytheHit extends ItemUtil implements Listener {

    @EventHandler
    public void onDeathsScytheHit(EntityDamageByEntityEvent e)   {

        if (!(playerHitPlayerCheck(e.getEntity(), e.getDamager(), e.getCause())))   return;
        Player attacker = (Player) e.getDamager();
        Player defender = (Player) e.getEntity();

        if (itemCheck(attacker.getInventory().getItemInMainHand(), "survivalShowdown.deathsScythe"))   {
            e.setDamage(0.0);
            double hp = defender.getHealth();
            double heal = hp * 0.2 * 0.25;
            hp *= 0.8;
            defender.setHealth(hp);
            if (attacker.getHealth() + heal <= attacker.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue())   {
                attacker.setHealth(attacker.getHealth() + heal);
            }
        }

    }

}
