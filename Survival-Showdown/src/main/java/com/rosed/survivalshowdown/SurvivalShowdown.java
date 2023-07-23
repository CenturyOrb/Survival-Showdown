package com.rosed.survivalshowdown;

import com.rosed.survivalshowdown.manager.InstanceManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SurvivalShowdown extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        InstanceManager.INSTANCE.start(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        InstanceManager.INSTANCE.end();
    }

}
