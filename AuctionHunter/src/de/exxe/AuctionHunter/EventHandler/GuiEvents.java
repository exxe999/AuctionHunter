package de.exxe.AuctionHunter.EventHandler;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.exxe.AuctionHunter.AuctionHandler.Auction;
import de.exxe.AuctionHunter.GUI.AuctionChest;
import de.exxe.AuctionHunter.GUI.StartGUI;
import de.exxe.AuctionHunter.GUI.ItemManager;
import de.exxe.AuctionHunter.Main.Main;

public class GuiEvents implements Listener {

	private Main main;
	private AuctionChest auctionChest;

	public GuiEvents(StartGUI gui, Main main) {
		this.main = main;
	}

	@EventHandler
	public void onInventoryClickItem(InventoryClickEvent event) {
		
		Inventory eInv = event.getView().getTopInventory();
		Player player = (Player) event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		auctionChest = new AuctionChest(main);
		
		if(item == null) {
			event.setCancelled(true);
			return;
		}
		//Events in Auktion Start
		if (eInv.getTitle().equals(ChatColor.translateAlternateColorCodes('&', "&8&lAuktion"))) {
			int startValue = main.getCustomConfig().get().getInt("auctionStartValue" + "." + player.getUniqueId());
			int slot =  event.getRawSlot();
			if (item.hasItemMeta()) {
				if (slot == 24) {
					if(startValue < 30) {
						new ItemManager(main).increaseStart(event.getInventory());
					}else {
						player.sendMessage("maximum");
					}
					event.setCancelled(true);
				}else if (slot == 42) {
					if(startValue > 0) {
						new ItemManager(main).decreaseStart(event.getInventory());
					}else {
						player.sendMessage("minimum");
					}
					event.setCancelled(true);
				}else if(slot == 0) {
					new AuctionChest(main).openAuctionChest(player);
				}else if(slot == 29 ||slot == 30 || slot == 38 ||slot == 39){
					Auction auction = new Auction(main);
					auction.setup(player);
					auction.startAuction();
				}else {
					event.setCancelled(true);
				}
			}
			event.setCancelled(true);
		}
		//Events in Auktionskiste
		else if(eInv.getTitle().equals(ChatColor.translateAlternateColorCodes('&', "&8&lDeine Auktionskiste"))) {
			if(event.getRawSlot() > 26 &&  event.getRawSlot() < 45) {
				if(event.getRawSlot() == 38) {
					auctionChest.deleteAuctionChest(player);
					new StartGUI(main).openStartGUI(player);
					event.setCancelled(true);
				}
				if(event.getRawSlot() == 42) {
					player.sendMessage("gespeichert");
					auctionChest.safeAuctionChest(player);
					new StartGUI(main).openStartGUI(player);
					event.setCancelled(true);
				}
				event.setCancelled(true);
			}


		}
	}

	@EventHandler
	public void onInventoryDrag(InventoryDragEvent event) {
		Inventory eInv = event.getView().getTopInventory();
		//Events in Auktion Start
		if (eInv.getTitle().equals(ChatColor.translateAlternateColorCodes('&', "&8&lAuktion"))) {
			event.setCancelled(true);
		}
		//Events in Auktionskiste
		if(eInv.getTitle().equals(ChatColor.translateAlternateColorCodes('&', "&8&lDeine Auktionskiste"))) {
			for(int i=27;i < 45;i++) {
				if(event.getRawSlots().contains(i)) {
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		Inventory eInv = event.getView().getTopInventory();
		if(eInv.getTitle().equals(ChatColor.translateAlternateColorCodes('&', "&8&lDeine Auktionskiste"))) {
			auctionChest.forceSafeAuctionChest((Player) event.getPlayer());
		}
	}
	
}
