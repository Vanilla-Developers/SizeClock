package com.damir00109.SizeClock;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SizeClock extends JavaPlugin {
    private static SizeClock instance;
    public static SizeClock getInstance() { return instance; }

    @Override
    public void onEnable() {
        instance = this;
        this.getCommand("sizeclock").setExecutor(new SizeClockCommand());
        getServer().getPluginManager().registerEvents(new SizeClockListener(this), this);
        getServer().getPluginManager().registerEvents(new SizeClockMenuListener(), this);
        // Генерация resource pack архива
        try {
            File pluginFolder = getDataFolder();
            if (!pluginFolder.exists()) pluginFolder.mkdirs();
            File zip = new File(pluginFolder, "sizeclock.zip");
            File resRoot = new File(getClass().getClassLoader().getResource("").toURI());
            File mcmeta = new File(resRoot, "pack.mcmeta");
            File assets = new File(resRoot, "assets");
            try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zip.toPath()))) {
                // pack.mcmeta
                if (mcmeta.exists()) {
                    zos.putNextEntry(new ZipEntry("pack.mcmeta"));
                    Files.copy(mcmeta.toPath(), zos);
                    zos.closeEntry();
                }
                // assets/**
                if (assets.exists()) {
                    zipFolder(zos, assets, "assets/");
                }
            }
            getLogger().info("Resource pack archive generated: " + zip.getAbsolutePath());
        } catch (Exception e) {
            getLogger().warning("Failed to generate resource pack: " + e.getMessage());
        }
        // Автоматическая отправка ресурс-пака при входе
        getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onJoin(PlayerJoinEvent event) {
                File zip = new File(getDataFolder(), "sizeclock.zip");
                if (zip.exists()) {
                    String url = zip.toURI().toString(); // file:///...
                    String sha1 = "0000000000000000000000000000000000000000"; // TODO: посчитать SHA1
                    event.getPlayer().setResourcePack(url, sha1);
                }
            }
        }, this);
        getLogger().info("SizeClock enabled!");
    }

    private void zipFolder(ZipOutputStream zos, File folder, String prefix) throws IOException {
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                zipFolder(zos, file, prefix + file.getName() + "/");
            } else {
                zos.putNextEntry(new ZipEntry(prefix + file.getName()));
                Files.copy(file.toPath(), zos);
                zos.closeEntry();
            }
        }
    }

    // Удаляю методы generateAndCopyDatapack, copyDatapackToWorld, copyFolder

    @Override
    public void onDisable() {
        getLogger().info("SizeClock disabled!");
    }
} 