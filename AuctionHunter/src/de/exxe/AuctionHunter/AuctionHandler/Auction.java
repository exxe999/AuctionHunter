package de.exxe.AuctionHunter.AuctionHandler;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.IUser;

import de.exxe.AuctionHunter.Main.Main;

public class Auction {
	
	public Main main;
	public Server server;
	public Player seller;
	public Player bidder;
	public double bid;
	public int startValue;
	public int highestBid;
	public Essentials ess;
	
	public Auction(Main main) {
		this.main = main;
		server = main.getServer();
		ess = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
	}
	
	public void setup(Player seller) {
		this.seller = seller;
		startValue = main.getCustomConfig().get().getInt("auctionStartValue" + "." + seller.getUniqueId());
		main.getCustomConfig().get().set("activeAuction.seller", seller.getUniqueId());
	}
	
	public boolean checkForAuctionRunning() {
		return main.getCustomConfig().get().getBoolean("activeAuction.isActive");
	}
	
	public void startAuction() {
		if(checkForAuctionRunning()) return;
		main.getCustomConfig().get().set("activeAuction.isActive", true);
		main.getCustomConfig().get().set("activeAuction.highestBid", startValue);
		main.getCustomConfig().saveConfig();
		server.broadcastMessage("Auktion gestartet! Bieten mit /auction [Betrag]");
		
		
		new BukkitRunnable() {    
	        
	        @Override
	        public void run() { 
	        	server.broadcastMessage("Gebot");
	        }                
		}
		.runTaskTimer(main, 20 * 60 * 1,1 * 20 * 60);
	}
	
	public void setHighestBid(Player bidder, double bid) {
		this.bidder = bidder;
		this.bid = bid;
		highestBid = main.getCustomConfig().get().getInt("activeAuction.highestBid");
		if(isValidBid()) {
			main.getCustomConfig().get().set("activeAuction.highestBid", bid);
		}else {
			//TODO:fehlermeldungen
		}
		main.getCustomConfig().saveConfig();
	}
	
	public boolean isValidBid() {
		IUser user = (bidder instanceof Player) ? ess.getUserMap().getUser(bidder.getUniqueId()) : null;
		if(user.getMoney().doubleValue() < bid || bid <= highestBid) {
			return false;
		}
		return true;
	}

}
