package de.exxe.AuctionHunter.EventHandler;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.exxe.AuctionHunter.GUI.AuctionChest;
import de.exxe.AuctionHunter.GUI.AuctionGUI;
import de.exxe.AuctionHunter.GUI.ItemManager;

public class GuiEvents implements Listener {

	private AuctionGUI gui;

	public GuiEvents(AuctionGUI gui) {
		this.gui = gui;
	}

	@EventHandler
	public void onInventoryClickItem(InventoryClickEvent event) {
		Inventory eInv = event.getView().getTopInventory();
		Player player = (Player) event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		
		if(item == null) {
			event.setCancelled(true);
			return;
		}
		
		if (eInv.getTitle().equals(ChatColor.translateAlternateColorCodes('&', "&8&lAuktion"))) {
			if (item.hasItemMeta()) {
				if (event.getRawSlot() == 24) {
					ItemManager.increaseStart(event.getInventory());
					event.setCancelled(true);
				}else if (event.getRawSlot() == 42) {
					ItemManager.decreaseStart(event.getInventory());
					event.setCancelled(true);
				}else if(event.getRawSlot() == 0) {
					new AuctionChest().openAuctionChest(player);
				}else {
					event.setCancelled(true);
				}
			}
			event.setCancelled(true);
		}
		else if(eInv.getTitle().equals(ChatColor.translateAlternateColorCodes('&', "&8&lDeine Auktionskiste"))) {
			if(event.getSlot() > 26 ) {
				event.setCancelled(true);
			}


		}
	}

	@EventHandler
	public void onInventoryDrag(InventoryDragEvent event) {
		System.out.println("drag");
		Inventory eInv = event.getView().getTopInventory();
		if (eInv.getTitle().equals(ChatColor.translateAlternateColorCodes('&', "&8&lAuktion"))) {
			event.setCancelled(true);
		}
		if(eInv.getTitle().equals(ChatColor.translateAlternateColorCodes('&', "&8&lDeine Auktionskiste"))) {

		}
	}
	
}
