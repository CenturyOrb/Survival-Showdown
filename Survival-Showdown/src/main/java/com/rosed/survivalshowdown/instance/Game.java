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
import org.bukkit.inventory.ItemFlag;
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
    private ItemStack[] stoneKit;
    private int player1Score = 0;
    private int player2Score = 0;

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
        setUpLiveInventory();

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
                player.getInventory().setContents(stoneKit);
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
        runTaskLater(survivalShowdown, 1200);

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
        playerList.forEach(player -> player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType())));
        gameScoreboard.getBoard().clearSlot(DisplaySlot.SIDEBAR);
        lobby.sendTitle(ChatColor.RED + "FINAL FIGHT", "");

    }

    public void nextRound(Player player)   {

        // teleport players to their locations in the arena
        // update score
        if (playerList.get(0) == player)   {
            player1Score++;
            if (player1Score == 3)   {
                end();
            }
        } else {
            player2Score++;
            if (player2Score == 3)   {
                end();
            }
        }

    }

    /**
     * ends the game
     */
    public void end()   {

        Bukkit.broadcastMessage("havent added end phase yet");
        // remove players from lobby playerlist
        // delete live worlds
        // reset Arena world
        // teleport players to Hub
        // clear their inventory, effects, experience

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
        livePotions.add(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 0, true, false));
        livePotions.add(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, true, false));

    }

    private void setUpLiveInventory()   {

        stoneKit = new ItemStack[4];

        ItemStack stoneSword = new ItemStack(Material.STONE_SWORD);
        ItemMeta stoneSwordMeta = stoneSword.getItemMeta();
        stoneSwordMeta.addEnchant(Enchantment.ARROW_DAMAGE, 0, true);
        stoneSwordMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        stoneSword.setItemMeta(stoneSwordMeta);

        ItemMeta stoneToolMeta = new ItemStack(Material.STONE_PICKAXE).getItemMeta();
        stoneToolMeta.addEnchant(Enchantment.DIG_SPEED, 3, true);
        stoneToolMeta.addEnchant(Enchantment.DURABILITY, 1, true);

        ItemStack stonePickaxe = new ItemStack(Material.STONE_PICKAXE);
        stonePickaxe.setItemMeta(stoneToolMeta);

        ItemStack stoneAxe = new ItemStack(Material.STONE_AXE);
        stoneAxe.setItemMeta(stoneToolMeta);

        ItemStack stoneShovel = new ItemStack(Material.STONE_SHOVEL);
        stoneShovel.setItemMeta(stoneToolMeta);

        stoneKit[0] = stoneSword;
        stoneKit[1] = stonePickaxe;
        stoneKit[2] = stoneAxe;
        stoneKit[3] = stoneShovel;

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
