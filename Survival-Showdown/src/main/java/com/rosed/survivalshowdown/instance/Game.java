package com.rosed.survivalshowdown.instance;

import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import com.rosed.survivalshowdown.SurvivalShowdown;
import com.rosed.survivalshowdown.manager.ConfigManager;
import com.rosed.survivalshowdown.manager.InstanceManager;
import com.rosed.survivalshowdown.manager.WorldManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Game extends BukkitRunnable {

    private final SurvivalShowdown survivalShowdown;
    private final ConfigManager configManager;
    private final WorldManager worldManager;

    private final Lobby lobby;
    private final int gameID;
    private GameState gameState;
    private List<Player> playerList;
    private Location player1ArenaLocation, player2ArenaLocation;
    private GameScoreboard gameScoreboard;
    private List<PotionEffect> livePotions;
    private Inventory liveInventory;

    public Game(Lobby lobby)   {

        survivalShowdown = InstanceManager.INSTANCE.getSurvivalShowdown();
        configManager = InstanceManager.INSTANCE.getConfigManager();
        worldManager = InstanceManager.INSTANCE.getWorldManager();

        this.lobby = lobby;
        this.gameID = lobby.getLobbyID();
        gameState = GameState.RECRUITING;
        playerList = new ArrayList<>();

        setPlayerArenaLocations();
        setUpLivePotions();

        System.out.println("Game: " + gameID + " is loaded");

    }

    /**
     * start the game, teleport players to the live worlds
     */
    public void start()   {

        playerList = lobby.getPlayerList();

        // teleport players to rightful worlds
        // add live state potion effects
        // give players stone tools kit
        for (Player player : playerList)   {
            if (worldManager.getLivePlayerWorldMap().get(player) != null)   {
                MultiverseWorld mvWorld = worldManager.getLivePlayerWorldMap().get(player).get(0);
                player.teleport(mvWorld.getSpawnLocation());
                player.addPotionEffects(livePotions);
            }
        }

        gameState = GameState.LIVE;
        lobby.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l-----------------------------------------------"));
        lobby.sendMessage(ChatColor.translateAlternateColorCodes('&', "&l                     Survival Showdown         "));
        lobby.sendMessage("");
        lobby.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e                  Gather resources to prepare  "));
        lobby.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e                  for the fight. First to 3 wins"));
        lobby.sendMessage("");
        lobby.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l-----------------------------------------------"));

        // start the timer for Arena Fight
        runTaskLater(survivalShowdown, 6000);

        gameScoreboard = new GameScoreboard(this);
        playerList.forEach(player -> player.setScoreboard(gameScoreboard.getBoard()));

    }

    /**
     * after Live state ends. Start arena fight
     */
    @Override
    public void run() {

        gameState = GameState.ARENA;
        playerList.get(0).teleport(player1ArenaLocation);
        playerList.get(1).teleport(player2ArenaLocation);
        gameScoreboard.getBoard().clearSlot(DisplaySlot.SIDEBAR);
        lobby.sendTitle(ChatColor.RED + "FINAL FIGHT", "");

    }

    /**
     * ends the game
     */
    public void end()   {

        // remove players from lobby playerlist
        // delete live worlds
        // reset Arena world
        // teleport players to Hub

    }

    /**
     * set player location to arena from config
     */
    private void setPlayerArenaLocations()   {

        player1ArenaLocation = configManager.getLocation("arena-location.player1", worldManager.getArenaWorldName(gameID));
        player2ArenaLocation = configManager.getLocation("arena-location.player2", worldManager.getArenaWorldName(gameID));

    }

    /**
     * potion effects for players during the live state
     */
    private void setUpLivePotions()   {

        livePotions = new ArrayList<>();
        livePotions.add(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 3, true, false));
        livePotions.add(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0, true, false));
        livePotions.add(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1, true, false));
        livePotions.add(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, true, false));

    }

    private void setUpLiveKit()   {

        liveInventory = Bukkit.createInventory(null, InventoryType.PLAYER);
        liveInventory.addItem(new ItemStack(Material.STONE_SWORD));
        ItemMeta stoneToolMeta = new ItemStack(Material.IRON_PICKAXE).getItemMeta();
        stoneToolMeta.addEnchant(Enchantment.DIG_SPEED, 2, true);

    }

    /**
     * sets game state
     * @param gameState GameState
     */
    public void setGameState(GameState gameState)   { this.gameState = gameState; }

    public void setPlayerList(List<Player> playerList)   {

        this.playerList = playerList;

    }

}
