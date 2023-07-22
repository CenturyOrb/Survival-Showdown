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
    private ConfigManager configManager;
    private MVWorldManager mvWorldManager;
    private WorldManager worldManager;
    private LobbyManager lobbyManager;
    private GameManager gameManager;

    /**
     * set up server
     * @param survivalShowdown SurvivalShowdown plugin instance
     */
    public void start(final SurvivalShowdown survivalShowdown)   {

        System.out.println("start()");

        assert survivalShowdown != null : "Error while starting Survival-Showdown";
        this.survivalShowdown = survivalShowdown;

        register();
    }

    /**
     *
     */
    public void end()   {

        worldManager.unloadWorlds();
        worldManager.deleteCopyWorlds();

    }

    /**
     * register managers, commands and events on startup
     */
    private void register() {
        // register plugin api
        mvCore = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        assert mvCore != null;

        // register managers
        configManager = new ConfigManager();
        mvWorldManager = mvCore.getMVWorldManager();
        worldManager = new WorldManager();
        lobbyManager = new LobbyManager();
        gameManager = new GameManager();


        System.out.println("register finished");
    }
}
