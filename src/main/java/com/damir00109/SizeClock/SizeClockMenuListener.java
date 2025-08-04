package com.damir00109.SizeClock;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SizeClockMenuListener implements Listener {
    private static final Map<UUID, Integer> selectedExp = new HashMap<>();
    private static final Map<UUID, Double> selectedSize = new HashMap<>();

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory inv = event.getInventory();
        if (!event.getView().getTitle().equals(SizeClockMenu.MENU_TITLE)) return;
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        UUID uuid = player.getUniqueId();
        ItemStack clock = player.getInventory().getItemInMainHand();
        Plugin plugin = SizeClock.getInstance();
        int maxLevel = player.getLevel();
        int exp = selectedExp.getOrDefault(uuid, 1);
        double size = selectedSize.getOrDefault(uuid, SizeClockItem.getSize(clock, plugin));
        int slot = event.getRawSlot();
        int stored = SizeClockItem.getExp(clock, plugin);

        switch (slot) {
            case 0: // - опыт
                if (exp > 1) exp--;
                selectedExp.put(uuid, exp);
                break;
            case 2: // + опыт
                if (exp < maxLevel) exp++;
                selectedExp.put(uuid, exp);
                break;
            case 1: // бутылочка "Залить"
                if (player.getLevel() >= exp && exp > 0) {
                    player.setLevel(player.getLevel() - exp);
                    int newStored = SizeClockItem.getExp(clock, plugin) + exp;
                    SizeClockItem.setExp(clock, plugin, newStored);
                    SizeClockItem.setLore(clock, newStored, size);
                    // Звук успешного залития
                    player.playSound(player.getLocation(), org.bukkit.Sound.BLOCK_BUBBLE_COLUMN_UPWARDS_INSIDE, 1.0f, 1.0f);
                }
                // Если опыта не хватает или exp == 0 — ничего не делать
                break;
            case 18: // - размер (уменьшить на 1, без траты опыта)
                int sliderPosDown = (int)Math.round((size * 10) - 4);
                if (sliderPosDown > 0) {
                    sliderPosDown--;
                    size = (4 + sliderPosDown) / 10.0;
                    selectedSize.put(uuid, size);
                    SizeClockItem.setLore(clock, stored, size);
                }
                break;
            case 26: // + размер (увеличить на 1, без траты опыта)
                int sliderPosUp = (int)Math.round((size * 10) - 4);
                if (sliderPosUp < 6) {
                    sliderPosUp++;
                    size = (4 + sliderPosUp) / 10.0;
                    selectedSize.put(uuid, size);
                    SizeClockItem.setLore(clock, stored, size);
                }
                break;
            default:
                // Клик по стеклянным панелям (19..25) ничего не делает
                break;
        }
        // Обновить меню с актуальными значениями
        int showExp = Math.min(selectedExp.getOrDefault(uuid, 1), player.getLevel());
        SizeClockMenu.open(player, clock, plugin, showExp, selectedSize.getOrDefault(uuid, SizeClockItem.getSize(clock, plugin)));
    }

    public static int getSelectedExp(Player player) {
        return selectedExp.getOrDefault(player.getUniqueId(), 1);
    }
    public static double getSelectedSize(Player player, ItemStack clock, Plugin plugin) {
        return selectedSize.getOrDefault(player.getUniqueId(), SizeClockItem.getSize(clock, plugin));
    }
    public static void reset(Player player) {
        selectedExp.remove(player.getUniqueId());
        selectedSize.remove(player.getUniqueId());
    }
}