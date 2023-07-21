package com.rosed.survivalshowdown.manager;

import com.rosed.survivalshowdown.SurvivalShowdown;
import lombok.Getter;
import org.bukkit.Location;

@Getter
public class ConfigManager {

    private final SurvivalShowdown survivalShowdown;
    

    public ConfigManager()   {
        survivalShowdown = InstanceManager.INSTANCE.getSurvivalShowdown();

        setUpConfig();

    }

    /**
     * sets up config
     */
    private void setUpConfig()   {

        survivalShowdown.getConfig().options().copyDefaults();
        survivalShowdown.saveDefaultConfig();

    }

}
