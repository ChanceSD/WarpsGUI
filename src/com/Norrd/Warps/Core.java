package com.Norrd.Warps;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {

	private List<String> warps;

	@Override
	public void onEnable() {
		if (getServer().getPluginManager().getPlugin("Essentials") == null) {
			getLogger().severe("Essentials not found. Disabling...");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}

		getCommand("warps").setExecutor(new CommandHandler(this));
		getServer().getPluginManager().registerEvents(new EventListener(), this);

		saveDefaultConfig();

		warps = new ArrayList<>();
		loadWarps();
	}

	public void loadWarps() {
		Plugin essentials = getServer().getPluginManager().getPlugin("Essentials");
		File warpDirectory = new File(essentials.getDataFolder() + File.separator + "warps");

		if ((!warpDirectory.exists()) || (!warpDirectory.isDirectory())) {
			return;
		}

		warps.clear();
		File[] arrayOfFile;
		int j = (arrayOfFile = warpDirectory.listFiles()).length;
		for (int i = 0; i < j; i++) {
			File file = arrayOfFile[i];
			String realName = file.getName().replace(".yml", "");
			String name2 = realName.substring(0, 1).toUpperCase() + realName.substring(1);
			warps.add(name2);
		}
	}

	public List<String> getWarps() {
		Collections.sort(warps);
		return warps;
	}
}
