package com.rosed.survivalshowdown.manager;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.rosed.survivalshowdown.SurvivalShowdown;
import lombok.Getter;
import org.bukkit.Bukkit;

@Getter
public enum InstanceManager {
    INSTANCE;

    private SurvivalShowdown survivalShowdown;

    private MultiverseCore mvCore;
    private MVWorldManager mvWorldManager;

    private ConfigManager configManager;

    /**
     * set up server
     * @param survivalShowdown SurvivalShowdown plugin instance
     */
    public void start(final SurvivalShowdown survivalShowdown)   {
        assert survivalShowdown != null : "Error while starting Survival-Showdown";
        this.survivalShowdown = survivalShowdown;

        register();
    }

    /**
     * register managers, commands and events on startup
     */
    private void register()   {

        // register plugin api
        mvCore = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");

        // register managers
        configManager = new ConfigManager();
        mvWorldManager = mvCore.getMVWorldManager();
    }
}
