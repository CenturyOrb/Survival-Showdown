package com.rosed.survivalshowdown.manager;

import com.rosed.survivalshowdown.SurvivalShowdown;
import com.rosed.survivalshowdown.instance.Craftable;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class CraftManager {

    private SurvivalShowdown survivalShowdown;

    private HashMap<Player, List<Craftable>> craftableMap;

    private ItemStack budgetGap;
    private ItemStack sharpEnchantmentBook;
    private ItemStack protectionEnchantmentBook;
    private ItemStack avidity;
    private ItemStack budgetPaper;
    private ItemStack budgetAnvil;
    private ItemStack quickPick;
    private ItemStack fortunate;
    private ItemStack ironPack;
    private ItemStack goldPack;
    private ItemStack mukluks;
    private ItemStack multiTool;
    private ItemStack starterSword;


    public CraftManager() {

        survivalShowdown = InstanceManager.INSTANCE.getSurvivalShowdown();

        craftableMap = new HashMap<>();

        setUpBudgetGapRecipe();
        setUpSharpnessEchantmentBook();
        setUpProtectionEnchantmentBook();
        setUpAvidity();
        setUpBudgetAnvil();
        setUpBudgetPaper();
        setUpFortunate();
        setUpQuickPick();
        setUpIronPack();
        setUpGoldPack();
        setUpMukluks();

    }

    /**
     * updates the Craftable in the map
     *
     * @param player player crafting
     * @param item   item being crafted
     * @param amount amount being crafted
     * @return true or false if the player can craft the item
     */
    public boolean updatePlayerCraftable(Player player, ItemStack item, int amount) {

        String localizedName = item.getItemMeta().getLocalizedName();

        for (Craftable craftable : craftableMap.get(player)) {
            if (craftable.getLocalizedName().equals(localizedName)) {
                return craftable.canCraftAmount(amount);
            }
        }
        return false;

    }

    /**
     * checks if an item is a Craftable item
     *
     * @param item item
     * @return true or false if item is a craftable
     */
    public boolean isCraftable(ItemStack item) {

        if (!item.getItemMeta().hasLocalizedName()) {
            return false;
        }

        for (Craftable craftable : getCraftablePreset()) {
            if (craftable.getLocalizedName().equals(item.getItemMeta().getLocalizedName())) {
                return true;
            }
        }
        return false;

    }

    /**
     * gets craftable preset of a new player
     *
     * @return return cratable preset
     */
    public List<Craftable> getCraftablePreset() {

        List<Craftable> craftables = new ArrayList<>();
        craftables.add(new Craftable(2, budgetGap));
        craftables.add(new Craftable(3, sharpEnchantmentBook));
        craftables.add(new Craftable(8, protectionEnchantmentBook));
        craftables.add(new Craftable(1, avidity));
        craftables.add(new Craftable(4, budgetAnvil));
        craftables.add(new Craftable(12, budgetPaper));
        craftables.add(new Craftable(2, fortunate));
        craftables.add(new Craftable(5, quickPick));
        craftables.add(new Craftable(48, ironPack));
        craftables.add(new Craftable(20, goldPack));
        craftables.add(new Craftable(1, mukluks));

        return craftables;

    }

    /**
     * sets up budget gaps recipe
     */
    private void setUpBudgetGapRecipe() {

        budgetGap = new ItemStack(Material.GOLDEN_APPLE);
        ItemMeta budgetGapMeta = budgetGap.getItemMeta();
        budgetGapMeta.setDisplayName(ChatColor.GOLD + "Budget Golden Apple");
        budgetGapMeta.setLocalizedName("survivalShowdown.bgap");
        budgetGap.setItemMeta(budgetGapMeta);

        ShapedRecipe budgetGapRecipe = new ShapedRecipe(new NamespacedKey(survivalShowdown, "bGap"), budgetGap);
        budgetGapRecipe.shape(
                " G ",
                "GAG",
                " G ");

        budgetGapRecipe.setIngredient('G', Material.GOLD_INGOT);
        budgetGapRecipe.setIngredient('A', Material.APPLE);

        Bukkit.addRecipe(budgetGapRecipe);

    }

    /**
     * sets up sharp book recipe
     */
    private void setUpSharpnessEchantmentBook() {

        sharpEnchantmentBook = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta sharpEnchantmentBookMeta = (EnchantmentStorageMeta) sharpEnchantmentBook.getItemMeta();
        sharpEnchantmentBookMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Sharpness Book");
        sharpEnchantmentBookMeta.setLocalizedName("survivalShowdown.sharpbook");
        sharpEnchantmentBookMeta.addStoredEnchant(Enchantment.DAMAGE_ALL, 1, true);
        sharpEnchantmentBook.setItemMeta(sharpEnchantmentBookMeta);

        ShapedRecipe sharpEnchantmentBookRecipe = new ShapedRecipe(new NamespacedKey(survivalShowdown, "sharpbook"), sharpEnchantmentBook);
        sharpEnchantmentBookRecipe.shape(
                "F  ",
                " PP",
                " PS");

        sharpEnchantmentBookRecipe.setIngredient('F', Material.FLINT);
        sharpEnchantmentBookRecipe.setIngredient('P', Material.PAPER);
        sharpEnchantmentBookRecipe.setIngredient('S', Material.IRON_SWORD);

        Bukkit.addRecipe(sharpEnchantmentBookRecipe);

    }

    private void setUpProtectionEnchantmentBook()   {

        protectionEnchantmentBook = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta protectionEnchantmentBookMeta = (EnchantmentStorageMeta) protectionEnchantmentBook.getItemMeta();
        protectionEnchantmentBookMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Protection Book");
        protectionEnchantmentBookMeta.setLocalizedName("survivalShowdown.protectionbook");
        protectionEnchantmentBookMeta.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        protectionEnchantmentBook.setItemMeta(protectionEnchantmentBookMeta);

        ShapedRecipe protectionEnchantmentBookRecipe = new ShapedRecipe(new NamespacedKey(survivalShowdown, "protectionbook"), protectionEnchantmentBook);
        protectionEnchantmentBookRecipe.shape(
                "   ",
                "  P",
                " PI");

        protectionEnchantmentBookRecipe.setIngredient('P', Material.PAPER);
        protectionEnchantmentBookRecipe.setIngredient('I', Material.IRON_INGOT);

        Bukkit.addRecipe(protectionEnchantmentBookRecipe);

    }

    private void setUpAvidity()   {

        avidity = new ItemStack(Material.GOLDEN_AXE);
        ItemMeta avidityMeta = avidity.getItemMeta();
        avidityMeta.setDisplayName(ChatColor.GREEN + "Avidity");
        avidityMeta.setLocalizedName("survivalShowdown.avidity");
        avidityMeta.addEnchant(Enchantment.DAMAGE_UNDEAD, 2, true);
        avidityMeta.addEnchant(Enchantment.DAMAGE_ARTHROPODS, 2, true);
        avidityMeta.addEnchant(Enchantment.LOOT_BONUS_MOBS, 2, true);
        avidity.setItemMeta(avidityMeta);

        ShapedRecipe avidityRecipe = new ShapedRecipe(new NamespacedKey(survivalShowdown, "avidity"), avidity);
        avidityRecipe.shape(
                " B ",
                " A ",
                " F ");

        avidityRecipe.setIngredient('F', Material.ROTTEN_FLESH);
        avidityRecipe.setIngredient('B', Material.BONE);
        avidityRecipe.setIngredient('A', Material.GOLDEN_AXE);

        Bukkit.addRecipe(avidityRecipe);

    }

    private void setUpQuickPick()   {

        quickPick = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta quickPickMeta = quickPick.getItemMeta();
        quickPickMeta.setDisplayName(ChatColor.GOLD + "Quick Pick");
        quickPickMeta.setLocalizedName("survivalShowdown.quickPick");
        quickPickMeta.addEnchant(Enchantment.DIG_SPEED, 2, true);
        quickPick.setItemMeta(quickPickMeta);

        ShapedRecipe quickPickRecipe = new ShapedRecipe(new NamespacedKey(survivalShowdown, "quickPick"), quickPick);
        quickPickRecipe.shape(
                "III",
                "CSC",
                " S ");

        quickPickRecipe.setIngredient('I', Material.RAW_IRON);
        quickPickRecipe.setIngredient('S', Material.STICK);
        quickPickRecipe.setIngredient('C', Material.COAL);

        Bukkit.addRecipe(quickPickRecipe);

    }

    private void setUpFortunate()   {

        fortunate = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta fortunateMeta = fortunate.getItemMeta();
        fortunateMeta.setDisplayName(ChatColor.GREEN + "Fortunate");
        fortunateMeta.setLocalizedName("survivalShowdown.fortunate");
        fortunateMeta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 3, true);
        Damageable damageable = (Damageable) fortunateMeta;
        damageable.setDamage(1559);
        fortunate.setItemMeta(fortunateMeta);

        ShapedRecipe fortunateRecipe = new ShapedRecipe(new NamespacedKey(survivalShowdown, "fortunate"), fortunate);
        fortunateRecipe.shape(
                "IGI",
                "LSL",
                " S ");

        fortunateRecipe.setIngredient('G', Material.RAW_GOLD);
        fortunateRecipe.setIngredient('I', Material.RAW_IRON);
        fortunateRecipe.setIngredient('L', Material.LAPIS_BLOCK);
        fortunateRecipe.setIngredient('S', Material.STICK);

        Bukkit.addRecipe(fortunateRecipe);

    }

    private void setUpBudgetAnvil()   {

        budgetAnvil = new ItemStack(Material.ANVIL);
        ItemMeta budgetAnvilMeta = budgetAnvil.getItemMeta();
        budgetAnvilMeta.setDisplayName(ChatColor.WHITE + "Budget Anvil");
        budgetAnvilMeta.setLocalizedName("survivalShowdown.budgetAnvil");
        budgetAnvil.setItemMeta(budgetAnvilMeta);

        ShapedRecipe budgetAnvilRecipe = new ShapedRecipe(new NamespacedKey(survivalShowdown, "budgetAnvil"), budgetAnvil);
        budgetAnvilRecipe.shape(
                "SSS",
                " B ",
                "SSS");

        budgetAnvilRecipe.setIngredient('B', Material.IRON_BLOCK);
        budgetAnvilRecipe.setIngredient('S', Material.IRON_INGOT);

        Bukkit.addRecipe(budgetAnvilRecipe);

    }

    private void setUpBudgetPaper()   {

        budgetPaper = new ItemStack(Material.PAPER);
        budgetPaper.setAmount(3);
        ItemMeta budgetPaperMeta = budgetPaper.getItemMeta();
        budgetPaperMeta.setDisplayName(ChatColor.WHITE + "Budget Paper");
        budgetPaperMeta.setLocalizedName("survivalShowdown.budgetPaper");
        budgetPaper.setItemMeta(budgetPaperMeta);

        ShapedRecipe budgetPaperRecipe = new ShapedRecipe(new NamespacedKey(survivalShowdown, "budgetPaper"), budgetPaper);
        budgetPaperRecipe.shape(
                "   ",
                "SS ",
                "   ");

        budgetPaperRecipe.setIngredient('S', Material.SUGAR_CANE);

        Bukkit.addRecipe(budgetPaperRecipe);

    }

    private void setUpIronPack()   {

        ironPack = new ItemStack(Material.IRON_INGOT, 12);
        ItemMeta ironPackMeta = ironPack.getItemMeta();
        ironPackMeta.setDisplayName(ChatColor.WHITE + "Iron Ingot");
        ironPackMeta.setLocalizedName("survivalShowdown.ironPack");
        ironPack.setItemMeta(ironPackMeta);

        ShapedRecipe ironPackRecipe = new ShapedRecipe(new NamespacedKey(survivalShowdown, "ironPack"), ironPack);
        ironPackRecipe.shape(
                "III",
                "ICI",
                "III");

        ironPackRecipe.setIngredient('C', Material.COAL);
        ironPackRecipe.setIngredient('I', Material.RAW_IRON);

        Bukkit.addRecipe(ironPackRecipe);

    }

    private void setUpGoldPack()   {

        goldPack = new ItemStack(Material.GOLD_INGOT, 10);
        ItemMeta goldPackMeta = ironPack.getItemMeta();
        goldPackMeta.setDisplayName(ChatColor.WHITE + "Gold Ingot");
        goldPackMeta.setLocalizedName("survivalShowdown.goldPack");
        goldPack.setItemMeta(goldPackMeta);

        ShapedRecipe goldPackRecipe = new ShapedRecipe(new NamespacedKey(survivalShowdown, "goldPack"), goldPack);
        goldPackRecipe.shape(
                "GGG",
                "GCG",
                "GGG");

        goldPackRecipe.setIngredient('C', Material.COAL);
        goldPackRecipe.setIngredient('G', Material.RAW_GOLD);

        Bukkit.addRecipe(goldPackRecipe);

    }

    private void setUpMukluks()   {

        mukluks = new ItemStack(Material.DIAMOND_BOOTS);
        ItemMeta mukluksMeta = mukluks.getItemMeta();
        mukluksMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Mukluks");
        mukluksMeta.setLocalizedName("survivalShowdown.mukluks");
        mukluksMeta.addEnchant(Enchantment.DEPTH_STRIDER, 2, true);
        mukluksMeta.addEnchant(Enchantment.DURABILITY, 3, true);
        mukluksMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 2, true);
        mukluks.setItemMeta(mukluksMeta);

        ShapedRecipe mukluksRecipe = new ShapedRecipe(new NamespacedKey(survivalShowdown, "mukluks"), mukluks);
        mukluksRecipe.shape(
                " W ",
                "WBW",
                " F ");

        ItemStack bottle = new ItemStack(Material.POTION, 1);
        ItemMeta meta = bottle.getItemMeta();
        PotionMeta pmeta = (PotionMeta) meta;
        PotionData pdata = new PotionData(PotionType.WATER);
        pmeta.setBasePotionData(pdata);
        bottle.setItemMeta(meta);

        mukluksRecipe.setIngredient('B', Material.DIAMOND_BOOTS);
        mukluksRecipe.setIngredient('W', bottle);
        mukluksRecipe.setIngredient('F', Material.FISHING_ROD);

        Bukkit.addRecipe(mukluksRecipe);
    }
}