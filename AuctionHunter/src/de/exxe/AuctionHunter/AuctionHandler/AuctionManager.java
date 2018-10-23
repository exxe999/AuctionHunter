package de.exxe.AuctionHunter.AuctionHandler;

import org.bukkit.entity.Player;

import de.exxe.AuctionHunter.Main.Main;

public class AuctionManager {
	
	public Main main;
	private Auction auction;
	public boolean isRunning = false;
	

	public AuctionManager(Main main) {
		this.main = main;
	}
	
	public void startAuction(Main main, Player seller) {
		auction = new Auction(main, seller);
	}
	
	public Auction getAuction() {
		return auction;
	}
	

}
