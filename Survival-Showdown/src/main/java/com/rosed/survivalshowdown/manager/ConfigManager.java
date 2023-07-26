package com.rosed.survivalshowdown.manager;

import com.rosed.survivalshowdown.SurvivalShowdown;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
public class ConfigManager {

    private final SurvivalShowdown survivalShowdown;
    private FileConfiguration config;

    private int numLobby;
    private String lobbyExampleName, lobbyFormat,
                    arenaExampleName, arenaFormat,
                    liveOverworldFormat, liveNetherFormat, liveEndFormat;
    private int lobbyCountdown;

    public ConfigManager()   {
        survivalShowdown = InstanceManager.INSTANCE.getSurvivalShowdown();

        setUpConfig();
        setConfigData();

    }

    /**
     * sets up config
     */
    private void setUpConfig()   {

        survivalShowdown.getConfig().options().copyDefaults();
        survivalShowdown.saveDefaultConfig();

        config = survivalShowdown.getConfig();

    }

    /**
     * initializes variables with config values
     */
    private void setConfigData()   {

        numLobby = config.getInt("num-lobby");

        lobbyExampleName = config.getString("lobby-example-name");
        lobbyFormat = config.getString("lobby-format");
        arenaExampleName = config.getString("arena-example-name");
        arenaFormat = config.getString("arena-format");
        lobbyCountdown = config.getInt("countdown.lobby");
        liveOverworldFormat = config.getString("live-format.overworld-format");
        liveNetherFormat = config.getString("live-format.nether-format");
        liveEndFormat = config.getString("live-format.end-format");

    }

    /**
     * gets location from config
     * @param configPath config path
     * @param worldName location world name
     * @return returns location from config
     */
    public Location getLocation(String configPath, String worldName)   {

        return new Location(
                Bukkit.getServer().getWorld(worldName),
                config.getInt(configPath + ".x"),
                config.getInt(configPath + ".y"),
                config.getInt(configPath + ".z"),
                config.getInt(configPath + ".pitch"),
                config.getInt(configPath + ".yaw")
        );

    }

}
