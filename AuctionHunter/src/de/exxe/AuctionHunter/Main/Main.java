package de.exxe.AuctionHunter.Main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.exxe.AuctionHunter.CustomConfig.CustomConfig;
import de.exxe.AuctionHunter.EventHandler.GuiEvents;
import de.exxe.AuctionHunter.GUI.AuctionGUI;
import de.exxe.AuctionHunter.Commands.Commands;

public class Main extends JavaPlugin{
	
	private CustomConfig config;
	
	@Override
	public void onEnable() {
		
		getCommand("auction").setExecutor(new Commands(this));
		getCommand("bid").setExecutor(new Commands(this));
		Bukkit.getPluginManager().registerEvents(new GuiEvents(new AuctionGUI(this)), this);
		config = new CustomConfig(this);
		reloadAllConfigs();
		
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public CustomConfig getCustomConfig() {
		return config;
	}
	
	public void reloadAllConfigs() {
		reloadConfig();
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

}
