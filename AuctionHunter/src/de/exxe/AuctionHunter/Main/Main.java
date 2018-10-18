package de.exxe.AuctionHunter.Main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.exxe.AuctionHunter.CustomConfig.CustomConfig;
import de.exxe.AuctionHunter.EventHandler.GuiEvents;
import de.exxe.AuctionHunter.GUI.StartGUI;
import de.exxe.AuctionHunter.AuctionHandler.AuctionManager;
import de.exxe.AuctionHunter.Commands.Commands;

public class Main extends JavaPlugin{
	
	private CustomConfig config;
	private AuctionManager auctionManager;
	
	@Override
	public void onEnable() {
		
		getCommand("auction").setExecutor(new Commands(this));
		getCommand("bid").setExecutor(new Commands(this));
		Bukkit.getPluginManager().registerEvents(new GuiEvents(new StartGUI(this),this), this);
		config = new CustomConfig(this);
		reloadAllConfigs();
		
		
	}
	
	@Override
	public void onLoad() {
		auctionManager = new AuctionManager(this);
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public CustomConfig getCustomConfig() {
		return config;
	}
	
	public AuctionManager getAuctionManager() {
		return auctionManager;
	}
	
	public void reloadAllConfigs() {
		reloadConfig();
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	//TODO: aktive auktion anzeigen lassen || bietren nur mit leerer claim kiste || man darf nicht selber mitbieten können || neue blöcke werden nicht erkannt

}
