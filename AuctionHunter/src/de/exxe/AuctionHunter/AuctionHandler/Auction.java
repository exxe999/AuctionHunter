package de.exxe.AuctionHunter.AuctionHandler;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.time.LocalTime;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.IUser;

import de.exxe.AuctionHunter.Main.Main;
import net.ess3.api.MaxMoneyException;

public class Auction {

	private Main main;
	private Server server;
	private Player seller;
	private Player bidder;
	private double startValue;
	private double highestBid;
	private double bid;
	private Essentials ess;
	private IUser bidderUser;
	private IUser sellerUser;
	private GregorianCalendar now = new GregorianCalendar();
    private DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
	private String date = df.format(now.getTime());
	private int count = 0;
	public BukkitRunnable counter;
	
	public Auction(Main main, Player seller) {
		this.main = main;
		this.server = main.getServer();
		this.seller = seller;
		ess = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
		startValue = main.getCustomConfig().get().getDouble(seller.getUniqueId() + "." + "auctionStartValue");
		startAuction();
	}
	
	public Player getSeller() {
		return seller;
	}

	public void startAuction() {
		main.getAuctionManager().isRunning = true;
		server.broadcastMessage(
				"[Auction] Starte Auktion! " + "Startwert: " + startValue + " Bieten mit /bid [Betrag]");
		setCounter();
		counter.runTaskTimerAsynchronously(main, 200, 200);
	}

	public void setHighestBid(Player bidder, double bid) {
		if (isValidBid(bidder, bid)) {
			this.bidder = bidder;
			this.bid = bid;
			server.broadcastMessage(bidder.getName() + " bietet " + bid);
			count = 1;
			counter.cancel();
			setCounter();
			counter.runTaskTimerAsynchronously(main, 0, 200);
		}
		
	}

	public boolean isValidBid(Player bidder, double bid) {
		bidderUser = (bidder instanceof Player) ? ess.getUserMap().getUser(bidder.getUniqueId()) : null;
		if (bidderUser.getMoney().doubleValue() < bid) {
			bidder.sendMessage("Du hast nicht genug Geld!");
			return false;
		} else if (bid <= highestBid) {
			bidder.sendMessage("Du musst höher bieten!");
			return false;
		}
		return true;
	}
	
	public void transferItemsMoney() {
		sellerUser = (seller instanceof Player) ? ess.getUserMap().getUser(seller.getUniqueId()) : null;
		try {
			sellerUser.giveMoney(new BigDecimal(highestBid));
			bidderUser.takeMoney(new BigDecimal(highestBid));
		} catch (MaxMoneyException e) {
			server.broadcastMessage("Interner Fehler Code: 123");
			e.printStackTrace();
		}
		List<?> items = main.getCustomConfig().get().getList(seller.getUniqueId() + "." + "auctionChestItems");
		main.getCustomConfig().get().set(seller.getUniqueId() + "." + "auctionChestItems", null);
		main.getCustomConfig().get().set(bidder.getUniqueId() + "." + "auctionClaimItems", items);
		bidder.sendMessage("Ersteigerte Items findest du hier /auktion claim");
		server.broadcastMessage("[Auktion] Transaktion erfolgreich");		
	}
	
	public void stopAuction() {
		startValue = main.getCustomConfig().get().getDouble(seller.getUniqueId() + "." + "auctionStartValue");
		server.broadcastMessage("Zuschlag für " + bidder.getName());
		main.getCustomConfig().get().set("AuctionLog | " + date.replace('.','-') + " "+ LocalTime.now().getHour() + ":"+ LocalTime.now().getMinute() + ":"+ LocalTime.now().getSecond() +"\n"+ "Seller:" + seller.getName() + "\nBidder:" + bidder.getName() + "\nSUCCESS!","Test");
		main.getCustomConfig().saveConfig();
		transferItemsMoney();
		main.getAuctionManager().isRunning = false;
	}
	
	public void setCounter() {
		counter = new BukkitRunnable() {
			@Override
			public void run() {
				if(count == -1) {
					server.broadcastMessage("[Auktion] Keine Gebote! Aktive Auktion gestoppt.");
					counter.cancel();
				}else if(count == 0) {
					server.broadcastMessage("[Auktion] Keine Gebote! Stoppe Auktion in 60 Sekunden.");
					count--;
				}else if(count == 1) {
					server.broadcastMessage("[Auction] Zum Ersten.");
					count++;
				}else if(count == 2) {
					server.broadcastMessage("[Auction] Zum Zweiten.");
					count++;
				} else if (count == 3) {
					server.broadcastMessage("[Auction] Zum Dritten.");
					server.broadcastMessage("[Auction] Verkauft!");
					stopAuction();
					counter.cancel();
				}
			}
				
		};
	}
	
}
