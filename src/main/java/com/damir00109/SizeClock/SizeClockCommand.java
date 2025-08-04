package com.damir00109.SizeClock;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SizeClockCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cТолько игрок может использовать эту команду!");
            return true;
        }
        if (!player.hasPermission("sizeclock.give")) {
            player.sendMessage("§cУ вас нет прав!");
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("give")) {
            player.getInventory().addItem(SizeClockItem.create(SizeClock.getInstance(), 0, 1.0));
            player.sendMessage("§aВы получили Часы размера!");
            return true;
        }
        player.sendMessage("§eИспользуйте: /sizeclock give");
        return true;
    }
} 