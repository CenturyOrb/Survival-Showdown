package com.rosed.survivalshowdown.manager;

import com.rosed.survivalshowdown.SurvivalShowdown;
import com.rosed.survivalshowdown.instance.Craftable;
import jdk.internal.org.jline.utils.DiffHelper;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
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
import java.util.UUID;

@Getter
public class CraftManager {

    private final SurvivalShowdown survivalShowdown;

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
    private ItemStack anduril;
    private ItemStack apprenticeHelm;
    private ItemStack exodus;
    private ItemStack tarnhelm;
    private ItemStack netherArtifact;
    private ItemStack hermesBoots;
    private ItemStack deathsScythe;
    private ItemStack excalibur;
    private ItemStack hideOfLeviathan;

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
        setUpAnduril();
        setUpApprenticeHelm();
        setUpExodus();
        setUpTarnhelm();
        setUpNetherArtifact();
        setUpHermesBoots();
        setUpDeathsScythe();
        setUpExcalibur();
        setUpHideofLeviathan();

    }

    /**
     * updates the Craftable in the map
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
        craftables.add(new Craftable(1, anduril));
        craftables.add(new Craftable(1, apprenticeHelm));
        craftables.add(new Craftable(1, exodus));
        craftables.add(new Craftable(1, tarnhelm));
        craftables.add(new Craftable(2, netherArtifact));
        craftables.add(new Craftable(1, hermesBoots));
        craftables.add(new Craftable(1, deathsScythe));
        craftables.add(new Craftable(1, excalibur));
        craftables.add(new Craftable(1, hideOfLeviathan));

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

    private void setUpAnduril()   {

        anduril = new ItemStack(Material.IRON_SWORD);
        ItemMeta andurilMeta = avidity.getItemMeta();
        andurilMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Anduril");
        andurilMeta.setLocalizedName("survivalShowdown.anduril");
        List<String> andurilLore = new ArrayList<>();
        andurilLore.add(ChatColor.GOLD + "While Holding:");
        andurilLore.add(ChatColor.GREEN + "- Resistance I");
        andurilLore.add(ChatColor.GREEN + "- Speed I");
        andurilMeta.setLore(andurilLore);
        andurilMeta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
        anduril.setItemMeta(andurilMeta);

        ShapedRecipe andurilRecipe = new ShapedRecipe(new NamespacedKey(survivalShowdown, "anduril"), anduril);
        andurilRecipe.shape(
                "FIF",
                "FIF",
                "FBF");

        andurilRecipe.setIngredient('F', Material.FEATHER);
        andurilRecipe.setIngredient('I', Material.IRON_BLOCK);
        andurilRecipe.setIngredient('B', Material.BLAZE_ROD);

        Bukkit.addRecipe(andurilRecipe);

    }

    private void setUpApprenticeHelm()   {

        apprenticeHelm = new ItemStack(Material.IRON_HELMET);
        ItemMeta apprenticeHelmMeta = apprenticeHelm.getItemMeta();
        apprenticeHelmMeta.setDisplayName(ChatColor.GREEN + "Apprentice Helm");
        apprenticeHelmMeta.setLocalizedName("survivalShowdown.apprenticeHelm");
        apprenticeHelmMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        apprenticeHelmMeta.addEnchant(Enchantment.PROTECTION_FIRE, 1, true);
        apprenticeHelmMeta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 1, true);
        apprenticeHelmMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 1, true);
        apprenticeHelm.setItemMeta(apprenticeHelmMeta);

        ShapedRecipe apprenticeHelmRecipe = new ShapedRecipe(new NamespacedKey(survivalShowdown, "apprenticeHelm"), apprenticeHelm);
        apprenticeHelmRecipe.shape(
                "III",
                "IRI",
                "   ");

        apprenticeHelmRecipe.setIngredient('R', Material.REDSTONE_TORCH);
        apprenticeHelmRecipe.setIngredient('I', Material.IRON_INGOT);

        Bukkit.addRecipe(apprenticeHelmRecipe);

    }

    private void setUpExodus()   {

        exodus = new ItemStack(Material.DIAMOND_HELMET);
        ItemMeta exodusMeta = exodus.getItemMeta();
        exodusMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Exodus");
        exodusMeta.setLocalizedName("survivalShowdown.exodus");
        exodusMeta.addEnchant(Enchantment.DIG_SPEED, 1, true);
        exodusMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        List<String> exodusLore = new ArrayList<>();
        exodusLore.add(ChatColor.GOLD + "While Wearing:");
        exodusLore.add(ChatColor.GREEN + "- Regeneration on hit");
        exodusMeta.setLore(exodusLore);
        exodus.setItemMeta(exodusMeta);

        ShapedRecipe exodusRecipe = new ShapedRecipe(new NamespacedKey(survivalShowdown, "exodus"), exodus);
        exodusRecipe.shape(
                "DDD",
                "DGD",
                "E E");

        exodusRecipe.setIngredient('D', Material.DIAMOND);
        exodusRecipe.setIngredient('G', Material.GOLDEN_APPLE);
        exodusRecipe.setIngredient('E', Material.EMERALD);

        Bukkit.addRecipe(exodusRecipe);

    }
    private void setUpTarnhelm()   {

        tarnhelm = new ItemStack(Material.DIAMOND_HELMET);
        ItemMeta tarnhelmMeta = tarnhelm.getItemMeta();
        tarnhelmMeta.setDisplayName(ChatColor.GREEN + "Tarnhelm");
        tarnhelmMeta.setLocalizedName("survivalShowdown.tarnhelm");
        tarnhelmMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        tarnhelmMeta.addEnchant(Enchantment.PROTECTION_FIRE, 1, true);
        tarnhelmMeta.addEnchant(Enchantment.WATER_WORKER, 3, true);
        tarnhelm.setItemMeta(tarnhelmMeta);

        ShapedRecipe tarnhelmRecipe = new ShapedRecipe(new NamespacedKey(survivalShowdown, "tarnhelm"), tarnhelm);
        tarnhelmRecipe.shape(
                "DID",
                "DRD",
                "   ");

        tarnhelmRecipe.setIngredient('D', Material.DIAMOND);
        tarnhelmRecipe.setIngredient('R', Material.REDSTONE_BLOCK);
        tarnhelmRecipe.setIngredient('I', Material.IRON_INGOT);

        Bukkit.addRecipe(tarnhelmRecipe);

    }

    private void setUpHideofLeviathan()   {

        hideOfLeviathan = new ItemStack(Material.DIAMOND_LEGGINGS);
        ItemMeta hideOfLeviathanMeta = hideOfLeviathan.getItemMeta();
        hideOfLeviathanMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Hide of Leviathan");
        hideOfLeviathanMeta.setLocalizedName("survivalShowdown.hideOfLeviathan");
        hideOfLeviathanMeta.addEnchant(Enchantment.OXYGEN, 3, true);
        hideOfLeviathanMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
        hideOfLeviathanMeta.addEnchant(Enchantment.WATER_WORKER, 1, true);
        hideOfLeviathan.setItemMeta(hideOfLeviathanMeta);

        ShapedRecipe hideOfLeviathanRecipe = new ShapedRecipe(new NamespacedKey(survivalShowdown, "hideOfLeviathan"), hideOfLeviathan);
        hideOfLeviathanRecipe.shape(
                "BWB",
                "DAD",
                "L L");

        hideOfLeviathanRecipe.setIngredient('L', Material.LILY_PAD);
        hideOfLeviathanRecipe.setIngredient('D', Material.DIAMOND);
        hideOfLeviathanRecipe.setIngredient('A', Material.DIAMOND_LEGGINGS);
        hideOfLeviathanRecipe.setIngredient('B', Material.LAPIS_BLOCK);
        hideOfLeviathanRecipe.setIngredient('W', Material.WATER_BUCKET);

        Bukkit.addRecipe(hideOfLeviathanRecipe);

    }
    private void setUpNetherArtifact()   {

        netherArtifact = new ItemStack(Material.BLAZE_ROD);
        ItemMeta netherArtifactMeta = netherArtifact.getItemMeta();
        netherArtifactMeta.setDisplayName(ChatColor.WHITE + "Nether Artifact");
        netherArtifactMeta.setLocalizedName("survivalShowdown.netherArtifact");
        netherArtifact.setItemMeta(netherArtifactMeta);

        ShapedRecipe netherArtifactRecipe = new ShapedRecipe(new NamespacedKey(survivalShowdown, "netherArtifact"), netherArtifact);
        netherArtifactRecipe.shape(
                "GLG",
                "GFG",
                "GLG");

        netherArtifactRecipe.setIngredient('F', Material.FIREWORK_ROCKET);
        netherArtifactRecipe.setIngredient('G', Material.ORANGE_STAINED_GLASS);
        netherArtifactRecipe.setIngredient('L', Material.LAVA_BUCKET);

        Bukkit.addRecipe(netherArtifactRecipe);

    }

    private void setUpHermesBoots()   {

        hermesBoots = new ItemStack(Material.DIAMOND_BOOTS);
        ItemMeta hermesBootsMeta = hermesBoots.getItemMeta();
        hermesBootsMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Hermes' Boots");
        hermesBootsMeta.setLocalizedName("survivalShowdown.hermesBoots");
        hermesBootsMeta.addEnchant(Enchantment.DURABILITY, 2, true);
        hermesBootsMeta.addEnchant(Enchantment.PROTECTION_FALL, 1, true);
        hermesBootsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
        List<String> hermesBootsLore = new ArrayList<>();
        hermesBootsLore.add(ChatColor.GOLD + "");
        hermesBootsLore.add(ChatColor.GOLD + "While Wearing:");
        hermesBootsLore.add(ChatColor.GREEN + "- Speed I");
        hermesBootsMeta.setLore(hermesBootsLore);
        hermesBoots.setItemMeta(hermesBootsMeta);

        ShapedRecipe hermesBootsRecipe = new ShapedRecipe(new NamespacedKey(survivalShowdown, "hermesBoots"), hermesBoots);
        hermesBootsRecipe.shape(
                "D D",
                "PBP",
                "F F");

        hermesBootsRecipe.setIngredient('B', Material.DIAMOND_BOOTS);
        hermesBootsRecipe.setIngredient('D', Material.DIAMOND);
        hermesBootsRecipe.setIngredient('P', Material.BLAZE_POWDER);
        hermesBootsRecipe.setIngredient('F', Material.FEATHER);

        Bukkit.addRecipe(hermesBootsRecipe);

    }

    private void setUpDeathsScythe()   {

        deathsScythe = new ItemStack(Material.IRON_HOE);
        ItemMeta deathsScytheMeta = deathsScythe.getItemMeta();
        deathsScytheMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Death's Scythe");
        deathsScytheMeta.setLocalizedName("survivalShowdown.deathsScythe");
        Damageable damageable = (Damageable) deathsScytheMeta;
        damageable.setDamage(240);
        List<String> deathsScytheLore = new ArrayList<>();
        deathsScytheLore.add(ChatColor.GOLD + "On Hit:");
        deathsScytheLore.add(ChatColor.GREEN + "- Deal 20% of target current hp");
        deathsScytheLore.add(ChatColor.GREEN + "- Heal 25% of damage dealt");
        deathsScytheMeta.setLore(deathsScytheLore);
        AttributeModifier damage = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 0.0D, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        deathsScytheMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damage);
        deathsScytheMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        deathsScythe.setItemMeta(deathsScytheMeta);

        ShapedRecipe deathsScytheRecipe = new ShapedRecipe(new NamespacedKey(survivalShowdown, "deathsScythe"), deathsScythe);
        deathsScytheRecipe.shape(
                "IIL",
                " BC",
                "B  ");

        deathsScytheRecipe.setIngredient('B', Material.BONE);
        deathsScytheRecipe.setIngredient('L', Material.LAPIS_BLOCK);
        deathsScytheRecipe.setIngredient('I', Material.IRON_INGOT);
        deathsScytheRecipe.setIngredient('C', Material.CLOCK);

        Bukkit.addRecipe(deathsScytheRecipe);

    }

    private void setUpExcalibur()   {

        excalibur = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta excaliburMeta = excalibur.getItemMeta();
        excaliburMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Excalibur");
        excaliburMeta.setLocalizedName("survivalShowdown.excalibur");
        List<String> excaliburLore = new ArrayList<>();
        excaliburLore.add("");
        excaliburLore.add(ChatColor.GOLD + "On Hit:");
        excaliburLore.add(ChatColor.GREEN + "- Explosion deals 2 hearts");
        excaliburLore.add(ChatColor.GREEN + "- 5 second cooldown");
        excaliburMeta.setLore(excaliburLore);
        excaliburMeta.addEnchant(Enchantment.DURABILITY, 2, true);
        excalibur.setItemMeta(excaliburMeta);

        ShapedRecipe excaliburRecipe = new ShapedRecipe(new NamespacedKey(survivalShowdown, "excalibur"), excalibur);
        excaliburRecipe.shape(
                "SFS",
                "STS",
                "SDS");

        excaliburRecipe.setIngredient('S', Material.SOUL_SAND);
        excaliburRecipe.setIngredient('D', Material.DIAMOND_SWORD);
        excaliburRecipe.setIngredient('T', Material.TNT);
        excaliburRecipe.setIngredient('F', Material.FIRE_CHARGE);

        Bukkit.addRecipe(excaliburRecipe);

    }

}