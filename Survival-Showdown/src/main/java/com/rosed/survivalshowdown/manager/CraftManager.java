package com.rosed.survivalshowdown.manager;

import com.rosed.survivalshowdown.SurvivalShowdown;
import com.rosed.survivalshowdown.instance.Craftable;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class CraftManager {

    private SurvivalShowdown survivalShowdown;

    private HashMap<Player, List<Craftable>> craftableMap;

    private ItemStack budgetGap;

    public CraftManager()   {

        survivalShowdown = InstanceManager.INSTANCE.getSurvivalShowdown();

        craftableMap = new HashMap<>();

        setUpBudgetGapRecipe();

    }

    /**
     * updates the Craftable in the map
     * @param player player crafting
     * @param item item being crafted
     * @param amount amount being crafted
     * @return true or false if the player can craft the item
     */
    public boolean updatePlayerCraftable(Player player, ItemStack item, int amount)   {

        String localizedName = item.getItemMeta().getLocalizedName();

        for (Craftable craftable : craftableMap.get(player))   {
            if (craftable.getLocalizedName().equals(localizedName))   {
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
    public boolean isCraftable(ItemStack item)   {

        if (!item.getItemMeta().hasLocalizedName())   { return false; }

        for (Craftable craftable : getCraftablePreset())   {
            if (craftable.getLocalizedName().equals(item.getItemMeta().getLocalizedName()))   {
                return true;
            }
        }
        return false;

    }

    /**
     * gets craftable preset of a new player
     * @return return cratable preset
     */
    public List<Craftable> getCraftablePreset()   {

        List<Craftable> craftables = new ArrayList<>();
        craftables.add(new Craftable(2, budgetGap));

        return craftables;

    }

    private void setUpBudgetGapRecipe()   {

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

}