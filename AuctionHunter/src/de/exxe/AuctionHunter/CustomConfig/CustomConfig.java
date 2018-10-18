package de.exxe.AuctionHunter.CustomConfig;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.exxe.AuctionHunter.Main.Main;

public class CustomConfig {
	private File f;
	private FileConfiguration cfg;
	
	public CustomConfig(Main main) {
		f = new File(main.getDataFolder(), "Data.yml");
		cfg = YamlConfiguration.loadConfiguration(f);
		
		if(!f.exists()) 
			try {
				f.createNewFile();
				} catch (IOException e) {
					System.err.println("RESTART SERVER TO FIX THIS ERROR CAUSED BY UNSTABLE SPIGOT VERSION!");
					main.getServer().broadcast("[AuctionHunter] Cannot be loaded caused unsable spigot version", "AuctionHunter.admin");
					e.printStackTrace(); 
				}
	}
	
	public void saveConfig() {
		try {
			cfg.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public FileConfiguration get() {
		return cfg;
	}

}

