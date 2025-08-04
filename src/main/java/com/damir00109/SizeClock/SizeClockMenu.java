package com.damir00109.SizeClock;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import java.util.ArrayList;
import java.util.List;
import com.destroystokyo.paper.profile.PlayerProfile;

public class SizeClockMenu {
    public static final String MENU_TITLE = "§6Часы размера";
    private static final String PLUS_HEAD_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjlmZjk4ODQyY2I2NjQwNWQyMGE4ZTZiMmVmZmFjNDYwMTBiOGY1NjAyZWE3MzI2ZDRkMTg1YjliNWRjZTA2In19fQ==";
    private static final String MINUS_HEAD_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjhkNWEzOGQ2YmZjYTU5Nzg2NDE3MzM2M2QyODRhOGQzMjljYWFkOTAxOGM2MzgxYjFiNDI5OWI4YjhiOTExYyJ9fX0=";
    private static final int[] PLUS_HEAD_ID = new int[]{-893989611,-464434990,-1671224071,254034430};
    private static final int[] MINUS_HEAD_ID = new int[]{-1422769225,1065437939,-1950961743,-1827461640};

    public static void open(Player player, ItemStack clock, Plugin plugin, int selectedExp, double selectedSize) {
        int exp = SizeClockItem.getExp(clock, plugin);
        int sliderPos = (int)Math.round((selectedSize * 10) - 4); // 4..10 -> 0..6
        if (sliderPos < 0) sliderPos = 0;
        if (sliderPos > 6) sliderPos = 6;
        int displaySize = 4 + sliderPos; // 4..10
        double size = displaySize / 10.0;
        int storedExp = exp;
        Inventory inv = Bukkit.createInventory(null, 27, MENU_TITLE);

        // Верхний ряд: - [0], Залить [1], + [2], Залито [8]
        inv.setItem(0, createHead("§c-", "Уменьшить опыт для заливки", MINUS_HEAD_TEXTURE, MINUS_HEAD_ID));
        if (selectedExp == 0) {
            ItemStack noExp = createExpBottle("§7Залить 0 ур.");
            ItemMeta meta = noExp.getItemMeta();
            meta.setDisplayName("§7Залить 0 ур.");
            List<String> lore = new ArrayList<>();
            lore.add("§8Недостаточно уровней");
            meta.setLore(lore);
            noExp.setItemMeta(meta);
            inv.setItem(1, noExp);
        } else {
            inv.setItem(1, createExpBottle("§eЗалить §7(" + selectedExp + " ур.)"));
        }
        inv.setItem(2, createHead("§a+", "Увеличить опыт для заливки", PLUS_HEAD_TEXTURE, PLUS_HEAD_ID));
        inv.setItem(8, createExpBottle("§bЗалито: §e" + storedExp + " ур."));

        // Ползунок размера (нижний ряд, 7 позиций)
        inv.setItem(18, createHead("§c-", "Уменьшить размер", MINUS_HEAD_TEXTURE, MINUS_HEAD_ID));
        for (int i = 0; i < 7; i++) {
            int slot = 19 + i;
            int val = 4 + i;
            if (i == sliderPos) {
                if (storedExp == 0) {
                    inv.setItem(slot, createSliderPanel("§cНет опыта для изменения размера", Material.BLACK_STAINED_GLASS_PANE));
                } else {
                    inv.setItem(slot, createSliderPanel("§dТекущий размер: " + val, Material.PINK_STAINED_GLASS_PANE));
                }
            } else if (i > sliderPos) {
                inv.setItem(slot, createSliderPanel("§a" + val, Material.LIME_STAINED_GLASS_PANE));
            } else {
                inv.setItem(slot, createSliderPanel("§c" + val, Material.RED_STAINED_GLASS_PANE));
            }
        }
        inv.setItem(26, createHead("§a+", "Увеличить размер", PLUS_HEAD_TEXTURE, PLUS_HEAD_ID));

        // Обновить лор предмета (опыт и выбранный размер)
        SizeClockItem.setLore(clock, storedExp, displaySize / 10.0);

        player.openInventory(inv);
    }

    private static ItemStack createHead(String name, String lore, String texture) {
        return createHead(name, lore, texture, null);
    }
    private static ItemStack createHead(String name, String lore, String texture, int[] customId) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta meta = skull.getItemMeta();
        meta.setDisplayName(name);
        if (lore != null) {
            List<String> l = new ArrayList<>();
            l.add("§7" + lore);
            meta.setLore(l);
        }
        if (meta instanceof org.bukkit.inventory.meta.SkullMeta skullMeta && texture != null && !texture.isEmpty()) {
            try {
                PlayerProfile profile = org.bukkit.Bukkit.createProfile(java.util.UUID.randomUUID());
                profile.setProperty(new com.destroystokyo.paper.profile.ProfileProperty("textures", texture));
                skullMeta.setPlayerProfile(profile);
                skull.setItemMeta(skullMeta);
                return skull;
            } catch (Exception ignored) {}
        }
        skull.setItemMeta(meta);
        return skull;
    }

    private static ItemStack createExpBottle(String name) {
        ItemStack item = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack createConfirm() {
        ItemStack item = new ItemStack(Material.LIME_DYE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§aПодтвердить");
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack createSliderPanel(String name, Material mat) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        if (name != null) meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack createCustomHead(String name, String lore, String texture) {
        return createHead(name, lore, texture, null);
    }
}
