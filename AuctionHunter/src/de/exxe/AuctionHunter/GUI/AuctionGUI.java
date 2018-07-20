package de.exxe.AuctionHunter.GUI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.exxe.AuctionHunter.Main.Main;

public class AuctionGUI {
	
	private Main main;
	
	public AuctionGUI(Main main) {
		this.main = main;
	}
	
	public void openAuctionGUI(Player player) {
		Inventory gui = Bukkit.createInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&8&lAuktion"));
		ItemStack goldItem = ItemManager.prepareItem("GOLD_INGOT",ChatColor.translateAlternateColorCodes('&', "&7Startwert: &610$"));
		ItemStack seperateItem = ItemManager.prepareItem("STAINED_GLASS_PANE"," ");
		ItemStack buttonUp = ItemManager.prepareItem("STONE_BUTTON",ChatColor.translateAlternateColorCodes('&', "&7Startwert: &610$ &8| &6\u25B2 &7Höher &6\u25B2"));
		ItemStack buttonDown = ItemManager.prepareItem("WOOD_BUTTON",ChatColor.translateAlternateColorCodes('&', "&7Startwert: &610$ &8| &6\u25BC &7Niedriger &6\u25BC"));
		ItemStack auctionChest = ItemManager.prepareItem("CHEST", ChatColor.translateAlternateColorCodes('&', "&aAuktionskiste"));
		seperateItem.setDurability((short) 7);
		gui.setItem(0, auctionChest);
		gui.setItem(24, buttonUp);
		gui.setItem(33, goldItem);
		gui.setItem(42, buttonDown);
		for(int i = 9;i <18;i++) {
			gui.setItem(i, seperateItem);
		}
		player.openInventory(gui);
	}
	
}
