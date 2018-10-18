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
	private double bid;
	private double startValue;
	private double highestBid;
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
		if (checkForAuctionRunning()) {
			seller.sendMessage("Es läuft bereits eine Auktion!");
			return;
		}
		startValue = main.getCustomConfig().get().getDouble(seller.getUniqueId() + "." + "auctionStartValue");
		startAuction();
	}

	public boolean checkForAuctionRunning() {
		return main.getCustomConfig().get().getBoolean("activeAuction.isActive");
	}

	public void startAuction() {
		main.getCustomConfig().get().set("activeAuction.isActive", true);
		main.getCustomConfig().get().set("activeAuction.isPaused", false);
		main.getCustomConfig().saveConfig();
		server.broadcastMessage(
				"[Auction] Starte Auktion! " + "Startwert: " + startValue + " Bieten mit /bid [Betrag]");
		setCounter();
		counter.runTaskTimerAsynchronously(main, 200, 200);
	}

	public void setHighestBid(Player bidder, double bid) {
		this.bidder = bidder;
		this.bid = bid;
		highestBid = main.getCustomConfig().get().getDouble("activeAuction.highestBid");
		if (isValidBid()) {
			main.getCustomConfig().get().set("activeAuction.highestBid", bid);
			server.broadcastMessage(bidder.getName() + " bietet " + bid);
			main.getCustomConfig().get().set("activeAuction.counter", 1);
			main.getCustomConfig().saveConfig();
			count++;
			counter.cancel();
			setCounter();
			counter.runTaskTimerAsynchronously(main, 0, 200);
		}
		
	}

	public boolean isValidBid() {
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
		main.getCustomConfig().get().set("AuctionLog | " + date.replace('.','-') + " "+ LocalTime.now(), main.getCustomConfig().get().get("activeAuction"));
		main.getCustomConfig().get().set("activeAuction", null);
		main.getCustomConfig().saveConfig();
		bidder.sendMessage("Ersteigerte Items findest du hier /auktion claim");
		server.broadcastMessage("[Auktion] Transaktion erfolgreich");		
	}
	
	public void stopAuction() {
		seller = Bukkit.getPlayer(UUID.fromString(main.getCustomConfig().get().getString("activeAuction.seller")));
		startValue = main.getCustomConfig().get().getDouble(seller.getUniqueId() + "." + "auctionStartValue");
		highestBid = main.getCustomConfig().get().getDouble("activeAuction.highestBid");
		server.broadcastMessage("Zuschlag für " + bidder.getName());
		transferItemsMoney();
	}
	
	public void setCounter() {
		counter = new BukkitRunnable() {
			@Override
			public void run() {
				if(count == -1) {
					server.broadcastMessage("[Auktion] Keine Gebote! Aktive Auktion gestoppt.");
					main.getCustomConfig().get().set("activeAuction", null);
					main.getCustomConfig().saveConfig();
					counter.cancel();
				}else if(count == 0) {
					server.broadcastMessage("[Auktion] Keine Gebote! Stoppe Auktion in 60 Sekunden.");
					main.getCustomConfig().get().set("activeAuction.counter", -1);
					main.getCustomConfig().saveConfig();
					count--;
				}else if(count == 1) {
					server.broadcastMessage("[Auction] Zum Ersten.");
					main.getCustomConfig().get().set("activeAuction.counter", 2);
					main.getCustomConfig().saveConfig();
					count++;
				}else if(count == 2) {
					server.broadcastMessage("[Auction] Zum Zweiten.");
					main.getCustomConfig().get().set("activeAuction.counter", 3);
					main.getCustomConfig().saveConfig();
					count++;
				} else if (count == 3) {
					server.broadcastMessage("[Auction] Zum Dritten.");
					server.broadcastMessage("[Auction] Verkauft!");
					main.getCustomConfig().get().set("activeAuction.counter", 100);
					main.getCustomConfig().saveConfig();
					stopAuction();
					counter.cancel();
				}
			}
				
		};
	}
	
}
