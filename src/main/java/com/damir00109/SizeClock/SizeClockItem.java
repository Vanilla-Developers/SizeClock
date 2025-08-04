package com.damir00109.SizeClock;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class SizeClockItem {
    public static final String KEY_EXP = "exp";
    public static final String KEY_SIZE = "size";

    public static ItemStack create(Plugin plugin, int exp, double size) {
        ItemStack item = new ItemStack(Material.HEART_OF_THE_SEA);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6Часы размера");
        meta.setCustomModelData(1); // ВАЖНО: для работы кастомной текстуры
        List<String> lore = new ArrayList<>();
        lore.add("§7ПКМ — применить размер");
        lore.add("§7Shift+ПКМ — открыть меню");
        lore.add("§7Опыт: " + exp);
        lore.add("§7Размер: " + String.format("%.1f", size));
        meta.setLore(lore);
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, KEY_EXP), PersistentDataType.INTEGER, exp);
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, KEY_SIZE), PersistentDataType.DOUBLE, size);
        item.setItemMeta(meta);
        return item;
    }

    public static boolean isSizeClock(ItemStack item, Plugin plugin) {
        if (item == null || item.getType() != Material.HEART_OF_THE_SEA) return false;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;
        return meta.getPersistentDataContainer().has(new NamespacedKey(plugin, KEY_EXP), PersistentDataType.INTEGER)
            && meta.getPersistentDataContainer().has(new NamespacedKey(plugin, KEY_SIZE), PersistentDataType.DOUBLE);
    }

    public static int getExp(ItemStack item, Plugin plugin) {
        if (item == null) return 0;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return 0;
        Integer val = meta.getPersistentDataContainer().get(new NamespacedKey(plugin, KEY_EXP), PersistentDataType.INTEGER);
        return val != null ? val : 0;
    }

    public static double getSize(ItemStack item, Plugin plugin) {
        if (item == null) return 1.0;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return 1.0;
        Double val = meta.getPersistentDataContainer().get(new NamespacedKey(plugin, KEY_SIZE), PersistentDataType.DOUBLE);
        return val != null ? val : 1.0;
    }

    public static void setExp(ItemStack item, Plugin plugin, int exp) {
        if (item == null) return;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, KEY_EXP), PersistentDataType.INTEGER, exp);
        item.setItemMeta(meta);
    }

    public static void setSize(ItemStack item, Plugin plugin, double size) {
        if (item == null) return;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, KEY_SIZE), PersistentDataType.DOUBLE, size);
        item.setItemMeta(meta);
    }

    public static void setLore(ItemStack item, int exp, double size) {
        if (item == null) return;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        List<String> lore = new ArrayList<>();
        lore.add("§7ПКМ — применить размер");
        lore.add("§7Shift+ПКМ — открыть меню");
        lore.add("§7Опыт: " + exp);
        lore.add("§7Размер: " + String.format("%.1f", size));
        meta.setLore(lore);
        item.setItemMeta(meta);
    }
} 