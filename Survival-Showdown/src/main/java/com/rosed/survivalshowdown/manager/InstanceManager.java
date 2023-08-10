package com.rosed.survivalshowdown.manager;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.rosed.survivalshowdown.SurvivalShowdown;
import com.rosed.survivalshowdown.command.CreateWorldCommand;
import com.rosed.survivalshowdown.command.LobbyCommand;
import com.rosed.survivalshowdown.command.WorldInfoCommand;
import com.rosed.survivalshowdown.listener.*;
import com.rosed.survivalshowdown.listener.itemlistener.*;
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
    private CraftManager craftManager;

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
     *
     */
    public void end()   {

        worldManager.unloadWorlds();
        worldManager.deleteCopyWorlds();
        worldManager.deleteLiveWorlds();

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
        craftManager = new CraftManager();
        gameManager = new GameManager();
        lobbyManager = new LobbyManager();

        // register commands
        survivalShowdown.getCommand("lobby").setExecutor(new LobbyCommand());
        survivalShowdown.getCommand("worldinfo").setExecutor(new WorldInfoCommand());
        survivalShowdown.getCommand("createworld").setExecutor(new CreateWorldCommand());

        // register events
        Bukkit.getPluginManager().registerEvents(new PlayerCraftEvent(), survivalShowdown);
        Bukkit.getPluginManager().registerEvents(new DiamondBreakEvent(), survivalShowdown);
        Bukkit.getPluginManager().registerEvents(new FishingRodPullEvent(), survivalShowdown);
        Bukkit.getPluginManager().registerEvents(new FortunateRepairEvent(), survivalShowdown);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathInArenaEvent(), survivalShowdown);
        Bukkit.getPluginManager().registerEvents(new DeathsScytheHit(), survivalShowdown);
        Bukkit.getPluginManager().registerEvents(new ExodusWear(), survivalShowdown);
        Bukkit.getPluginManager().registerEvents(new ExcaliburHit(), survivalShowdown);
        new AndurilHold();
        new FutureHit();
        new HermesBootsWear();

    }
}
