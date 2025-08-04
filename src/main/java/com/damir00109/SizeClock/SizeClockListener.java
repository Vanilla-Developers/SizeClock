package com.damir00109.SizeClock;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Particle;
import org.bukkit.Sound;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.entity.LivingEntity;

public class SizeClockListener implements Listener {
    private final Plugin plugin;
    public SizeClockListener(Plugin plugin) { this.plugin = plugin; }

    // Для антиспама: карта UUID -> время следующего разрешённого клика (System.currentTimeMillis())
    private static final Map<UUID, Long> clickCooldown = new HashMap<>();
    private static final long COOLDOWN_MS = 500;

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        // Только основная рука
        if (event.getHand() != EquipmentSlot.HAND) return;
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item == null || !SizeClockItem.isSizeClock(item, plugin)) return;
        // Только ПКМ по воздуху или блоку
        if (event.getAction() != org.bukkit.event.block.Action.RIGHT_CLICK_AIR && event.getAction() != org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK) return;
        event.setCancelled(true);
        if (player.isSneaking()) {
            int selectedExp = SizeClockMenuListener.getSelectedExp(player);
            double selectedSize = SizeClockMenuListener.getSelectedSize(player, item, plugin);
            SizeClockMenu.open(player, item, plugin, selectedExp, selectedSize);
        } else {
            AttributeInstance scaleAttr = player.getAttribute(Attribute.SCALE);
            if (scaleAttr != null) {
                double current = scaleAttr.getBaseValue();
                double selectedSize = SizeClockMenuListener.getSelectedSize(player, item, plugin);
                int stored = SizeClockItem.getExp(item, plugin);
                UUID uuid = player.getUniqueId();
                long now = System.currentTimeMillis();
                if (clickCooldown.containsKey(uuid) && now < clickCooldown.get(uuid)) {
                    return;
                }
                clickCooldown.put(uuid, now + COOLDOWN_MS);
                if (Math.abs(current - selectedSize) > 0.001 && Math.abs(current - 1.0) < 0.001) {
                    // Применить выбранный размер (тратим опыт)
                    if (stored > 0) {
                        int steps = 10;
                        double start = current;
                        double end = selectedSize;
                        double delta = (end - start) / steps;
                        new BukkitRunnable() {
                            int tick = 0;
                            double value = start;
                            @Override
                            public void run() {
                                if (tick >= steps) {
                                    scaleAttr.setBaseValue(end);
                                    this.cancel();
                                    SizeClockItem.setExp(item, plugin, stored - 1);
                                    SizeClockItem.setLore(item, stored - 1, end);
                                    return;
                                }
                                value += delta;
                                scaleAttr.setBaseValue(value);
                                // Эффекты
                                player.getWorld().spawnParticle(Particle.SCULK_SOUL, player.getLocation().add(0,1,0), 10, 0.5, 0.5, 0.5, 0.01);
                                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 1.0f, 1.0f);
                                tick++;
                            }
                        }.runTaskTimer(plugin, 0, 2);
                    } else {
                        // Нет опыта — звук
                        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1.0f, 1.0f);
                    }
                } else if (Math.abs(current - 1.0) > 0.001) {
                    // Второй клик — сброс к 1.0 (тоже тратим опыт)
                    if (stored > 0) {
                        int steps = 10;
                        double start = current;
                        double end = 1.0;
                        double delta = (end - start) / steps;
                        new BukkitRunnable() {
                            int tick = 0;
                            double value = start;
                            @Override
                            public void run() {
                                if (tick >= steps) {
                                    scaleAttr.setBaseValue(end);
                                    this.cancel();
                                    SizeClockItem.setExp(item, plugin, stored - 1);
                                    SizeClockItem.setLore(item, stored - 1, end);
                                    return;
                                }
                                value += delta;
                                scaleAttr.setBaseValue(value);
                                // Эффекты
                                player.getWorld().spawnParticle(Particle.SCULK_SOUL, player.getLocation().add(0,1,0), 10, 0.5, 0.5, 0.5, 0.01);
                                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 1.0f, 1.0f);
                                tick++;
                            }
                        }.runTaskTimer(plugin, 0, 2);
                    } else {
                        // Нет опыта — звук
                        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1.0f, 1.0f);
                    }
                }
                // если размер не меняется — ничего не делать
            } else {
                player.sendMessage("§cОшибка: невозможно изменить размер (атрибут не найден)");
            }
        }
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        // Только основная рука и только ПКМ
        if (event.getHand() != EquipmentSlot.HAND) return;
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItem(event.getHand());
        if (item == null || !SizeClockItem.isSizeClock(item, plugin)) return;
        if (!(event.getRightClicked() instanceof LivingEntity target)) return;
        if (target == player) return; // себя не трогаем
        event.setCancelled(true);
        AttributeInstance scaleAttr = target.getAttribute(Attribute.SCALE);
        if (scaleAttr == null) return;
        double current = scaleAttr.getBaseValue();
        double selectedSize = SizeClockMenuListener.getSelectedSize(player, item, plugin);
        int stored = SizeClockItem.getExp(item, plugin);
        UUID uuid = player.getUniqueId();
        long now = System.currentTimeMillis();
        if (clickCooldown.containsKey(uuid) && now < clickCooldown.get(uuid)) {
            return;
        }
        clickCooldown.put(uuid, now + COOLDOWN_MS);
        if (Math.abs(current - selectedSize) > 0.001 && Math.abs(current - 1.0) < 0.001) {
            // Применить выбранный размер (тратим опыт)
            if (stored > 0) {
                int steps = 10;
                double start = current;
                double end = selectedSize;
                double delta = (end - start) / steps;
                new BukkitRunnable() {
                    int tick = 0;
                    double value = start;
                    @Override
                    public void run() {
                        if (tick >= steps) {
                            scaleAttr.setBaseValue(end);
                            this.cancel();
                            SizeClockItem.setExp(item, plugin, stored - 1);
                            SizeClockItem.setLore(item, stored - 1, end);
                            return;
                        }
                        value += delta;
                        scaleAttr.setBaseValue(value);
                        // Эффекты
                        target.getWorld().spawnParticle(Particle.SCULK_SOUL, target.getLocation().add(0,1,0), 10, 0.5, 0.5, 0.5, 0.01);
                        target.getWorld().playSound(target.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 1.0f, 1.0f);
                        tick++;
                    }
                }.runTaskTimer(plugin, 0, 2);
            } else {
                // Нет опыта — звук
                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1.0f, 1.0f);
            }
        } else if (Math.abs(current - 1.0) > 0.001) {
            // Второй клик — сброс к 1.0 (тоже тратим опыт)
            if (stored > 0) {
                int steps = 10;
                double start = current;
                double end = 1.0;
                double delta = (end - start) / steps;
                new BukkitRunnable() {
                    int tick = 0;
                    double value = start;
                    @Override
                    public void run() {
                        if (tick >= steps) {
                            scaleAttr.setBaseValue(end);
                            this.cancel();
                            SizeClockItem.setExp(item, plugin, stored - 1);
                            SizeClockItem.setLore(item, stored - 1, end);
                            return;
                        }
                        value += delta;
                        scaleAttr.setBaseValue(value);
                        // Эффекты
                        target.getWorld().spawnParticle(Particle.SCULK_SOUL, target.getLocation().add(0,1,0), 10, 0.5, 0.5, 0.5, 0.01);
                        target.getWorld().playSound(target.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 1.0f, 1.0f);
                        tick++;
                    }
                }.runTaskTimer(plugin, 0, 2);
            } else {
                // Нет опыта — звук
                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1.0f, 1.0f);
            }
        }
    }
} 