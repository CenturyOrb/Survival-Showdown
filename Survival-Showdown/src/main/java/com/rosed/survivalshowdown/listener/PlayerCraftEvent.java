package com.rosed.survivalshowdown.listener;

import com.rosed.survivalshowdown.manager.CraftManager;
import com.rosed.survivalshowdown.manager.InstanceManager;
import com.rosed.survivalshowdown.manager.LobbyManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerCraftEvent implements Listener {

    private LobbyManager lobbyManager;
    private CraftManager craftManager;

    public PlayerCraftEvent()   {

        lobbyManager = InstanceManager.INSTANCE.getLobbyManager();
        craftManager = InstanceManager.INSTANCE.getCraftManager();


    }

    @EventHandler
    public void onCraft(CraftItemEvent e) {

        Player player = (Player) e.getWhoClicked();

        ItemStack item = e.getRecipe().getResult().clone();
        ClickType click = e.getClick();

        int recipeAmount = item.getAmount();

        switch (click) {
            case NUMBER_KEY:
                if (e.getWhoClicked().getInventory().getItem(e.getHotbarButton()) != null)
                    recipeAmount = 0;
                break;
            case SWAP_OFFHAND:
                player.sendMessage("Offhand " + e.getWhoClicked().getInventory().getItemInOffHand().getI18NDisplayName());
                if (e.getWhoClicked().getInventory().getItemInOffHand().getType() != Material.AIR)   {
                    recipeAmount = 0;
                    break;
                }
            case DROP:
            case CONTROL_DROP:
                ItemStack cursor = e.getCursor();
                if (cursor != null && cursor.getType() != Material.AIR)
                    recipeAmount = 0;
                break;
            case SHIFT_RIGHT:
            case SHIFT_LEFT:
                if (recipeAmount == 0)
                    break;

                int maxCraftable = getMaxCraftAmount(e.getInventory());
                int capacity = fits(item, e.getView().getBottomInventory());

                if (capacity < maxCraftable)
                    maxCraftable = ((capacity + recipeAmount - 1) / recipeAmount) * recipeAmount;

                recipeAmount = maxCraftable;
                break;
            default:
        }

        if (recipeAmount == 0)
            return;

        item.setAmount(recipeAmount);

        player.sendMessage(ChatColor.GOLD + "" + recipeAmount);

        if (lobbyManager.getPlayerLobby(player) == null)   {

            e.setCancelled(true);

        } else {
            if (craftManager.isCraftable(item))   {
                if (!craftManager.updatePlayerCraftable(player, item, recipeAmount))   {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c(&l&6!&c) You can't craft more " + item.getItemMeta().getDisplayName()));
                    e.setCancelled(true);
                }
            }
        }

    }

    public static int getMaxCraftAmount(CraftingInventory inv) {
        if (inv.getResult() == null)
            return 0;

        int resultCount = inv.getResult().getAmount();
        int materialCount = Integer.MAX_VALUE;

        for (ItemStack is : inv.getMatrix())
            if (is != null && is.getAmount() < materialCount)
                materialCount = is.getAmount();

        return resultCount * materialCount;
    }

    public static int fits(ItemStack stack, Inventory inv) {
        ItemStack[] contents = inv.getContents();
        int result = 0;

        for (ItemStack is : contents)
            if (is == null)
                result += stack.getMaxStackSize();
            else if (is.isSimilar(stack))
                result += Math.max(stack.getMaxStackSize() - is.getAmount(), 0);

        return result;
    }

}
