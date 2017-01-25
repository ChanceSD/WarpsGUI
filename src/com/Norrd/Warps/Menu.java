package com.Norrd.Warps;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Menu {
	private Core plugin;

	public Menu(Core instance) {
		plugin = instance;
	}

	public void open(Player player) {
		int size = plugin.getWarps().size();
		int result = Math.round(size / 9);

		if ((result < size) && (result * 9 != size)) {
			result++;
		}

		org.bukkit.inventory.Inventory inventory = Bukkit.createInventory(null, result * 9, ChatColor.translateAlternateColorCodes('&', "&b&lWarp menu"));

		String nameColor = plugin.getConfig().getString("display-item-name-color", "&a");
		ChatColor color = parseChatColor(nameColor);

		String lore = plugin.getConfig().getString("display-item-lore", "&2Click to warp");
		lore = replaceColors(lore);

		for (String warp : plugin.getWarps()) {
			String item = plugin.getConfig().getString("display-item", "WOOL");
			ItemStack stack = parseItemStack(item);
			stack.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.DURABILITY, 10);
			ItemStack finalStack = createItemStack(stack, color + warp, new String[] { lore });
			inventory.addItem(new ItemStack[] { finalStack });
		}

		player.openInventory(inventory);
	}

	@SuppressWarnings("deprecation")
	private ItemStack parseItemStack(String in) {
		Material material = null;
		byte data = 0;

		if (in.contains(":")) {
			String[] split = in.split(":");
			String m = split[0];
			String d = split[1];
			try {
				material = Material.valueOf(m);
			} catch (IllegalArgumentException ex) {
				Bukkit.getLogger().warning(m + " is not a valid Material! Using default: WOOL");
				material = Material.WOOL;
			}

			if ((d.equalsIgnoreCase("r")) || (d.equalsIgnoreCase("random"))) {
				java.util.Random random = new java.util.Random();
				DyeColor randColor = DyeColor.values()[random.nextInt(DyeColor.values().length)];

				if (material == Material.WOOL) {
					data = randColor.getWoolData();
				} else {
					data = randColor.getDyeData();
				}
			} else {
				try {
					data = Byte.parseByte(d);
				} catch (NumberFormatException ex) {
					Bukkit.getLogger().warning(d + " is not a valid data! Using default: 0");
					data = 0;
				}
			}
		} else {
			try {
				material = Material.valueOf(in);
			} catch (IllegalArgumentException ex) {
				Bukkit.getLogger().warning(in + " is not a valid Material! Using default: WOOL");
				material = Material.WOOL;
			}
		}

		return new ItemStack(material, 1, data);
	}

	private ChatColor parseChatColor(String in) {
		ChatColor chatColor = ChatColor.WHITE;
		try {
			chatColor = ChatColor.valueOf(in);
		} catch (IllegalArgumentException localIllegalArgumentException) {
		}
		ChatColor[] arrayOfChatColor;
		int j = (arrayOfChatColor = ChatColor.values()).length;
		for (int i = 0; i < j; i++) {
			ChatColor color = arrayOfChatColor[i];
			if (in.substring(1).equals(String.valueOf(color.getChar()))) {
				chatColor = color;
				break;
			}
		}

		return chatColor;
	}

	private String replaceColors(String in) {
		return ChatColor.translateAlternateColorCodes('&', in);
	}

	private ItemStack createItemStack(ItemStack stack, String name, String... lores) {
		ItemMeta meta = stack.getItemMeta();

		meta.setDisplayName(name);

		List<String> loreList = new ArrayList<>();
		String[] arrayOfString;
		int j = (arrayOfString = lores).length;
		for (int i = 0; i < j; i++) {
			String lore = arrayOfString[i];
			loreList.add(lore);
		}

		meta.setLore(loreList);

		stack.setItemMeta(meta);
		return stack;
	}
}
