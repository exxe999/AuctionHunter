package de.exxe.AuctionHunter.GUI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AuctionChest {
	
	public void openAuctionChest(Player player) {
		
		Inventory gui = Bukkit.createInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&8&lDeine Auktionskiste"));
		ItemStack seperateItem = ItemManager.prepareItem("STAINED_GLASS_PANE"," ");
		ItemStack saveItem = ItemManager.prepareItem("STAINED_GLASS_PANE", ChatColor.translateAlternateColorCodes('&', "&8Speichern"));
		ItemStack abortItem = ItemManager.prepareItem("STAINED_GLASS_PANE", ChatColor.translateAlternateColorCodes('&', "&8Abbrechen"));
		saveItem.setDurability((short) 5);
		abortItem.setDurability((short) 14);
		seperateItem.setDurability((short) 7);
		for(int i = 27;i <36;i++) {
			gui.setItem(i, seperateItem);
		}
		gui.setItem(42, saveItem);
		gui.setItem(38, abortItem);
		
		player.openInventory(gui);
		
	}

}
