package com.Norrd.Warps;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler implements org.bukkit.command.CommandExecutor {
	private Core plugin;

	public CommandHandler(Core instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("warps")) {
			if (!(sender instanceof Player)) {
				return true;
			}

			Player player = (Player) sender;

			if (args.length == 0) {
				if (!player.hasPermission("warpGUI.open")) {
					player.sendMessage(ChatColor.RED + "You don't have permission to do this.");
					return true;
				}

				if (plugin.getWarps().size() == 0) {
					player.sendMessage(ChatColor.RED + "No warps found.");
					return true;
				}

				Menu menu = new Menu(plugin);
				menu.open(player);
				return true;
			}

			if (args[0].equalsIgnoreCase("reload")) {
				if (!player.hasPermission("warpgui.reload")) {
					player.sendMessage(ChatColor.RED + "You don't have permission to do this.");
					return true;
				}

				plugin.loadWarps();
				player.sendMessage(ChatColor.GREEN + "Warps reloaded.");
				return true;
			}
		}

		return false;
	}
}
