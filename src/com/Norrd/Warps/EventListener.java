package com.Norrd.Warps;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EventListener implements org.bukkit.event.Listener {
	@org.bukkit.event.EventHandler
	public void onClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		Inventory inventory = event.getInventory();

		if (!inventory.getTitle().equals(ChatColor.translateAlternateColorCodes('&', "&b&lWarp menu"))) {
			return;
		}

		event.setCancelled(true);

		if (event.getAction() != InventoryAction.PICKUP_ALL) {
			return;
		}

		ItemStack stack = event.getCurrentItem();

		if (stack == null) {
			return;
		}

		if (!stack.hasItemMeta()) {
			return;
		}

		ItemMeta meta = stack.getItemMeta();

		if (!meta.hasDisplayName()) {
			return;
		}

		String warp = ChatColor.stripColor(meta.getDisplayName());
		org.bukkit.Bukkit.getServer().dispatchCommand(player, "warp " + warp);
	}
}
