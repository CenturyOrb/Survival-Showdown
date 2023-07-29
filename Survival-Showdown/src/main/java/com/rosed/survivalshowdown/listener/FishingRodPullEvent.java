package com.rosed.survivalshowdown.listener;

import com.rosed.survivalshowdown.manager.InstanceManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class FishingRodPullEvent implements Listener {

//    @EventHandler
//    public void onEntityDamageByEntity(ProjectileHitEvent event) {
//        if (event.getEntity() instanceof FishHook) { // If damager is an egg
//            final Entity hurt = event.getHitEntity(); // Declare entity final so that we can use it in future scheduling
//            Bukkit.getScheduler().scheduleSyncDelayedTask(InstanceManager.INSTANCE.getSurvivalShowdown(), new Runnable() { // Schedule a delayed task to run in one tick, plugin is main class
//                public void run() {
//                    hurt.setVelocity(new Vector(0.2, 0.2, 0.2)); // Set velocity to zero vector, cancelling all motion
//                }
//            });
//        }
//    }

    @EventHandler
    public void onFish(PlayerFishEvent event) {
        if (event.getState().equals(PlayerFishEvent.State.CAUGHT_ENTITY)) {
            event.setCancelled(true);
            event.getHook().remove();
        }
    }

    @EventHandler
    public void onHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof FishHook && event.getHitEntity() != null) {
            FishHook hook = (FishHook) event.getEntity();
            LivingEntity entity = (LivingEntity) event.getHitEntity();

            if (entity == null) {
                return;
            }

            Vector knockback = hook.getOrigin().toVector().subtract(entity.getLocation().toVector()).normalize();

            entity.damage(0.1);
            entity.setVelocity(new Vector(-knockback.getX() * 1.1, 0.45, -knockback.getZ() * 1.1));
        }
    }
}
