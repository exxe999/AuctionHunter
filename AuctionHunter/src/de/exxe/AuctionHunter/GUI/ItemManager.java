package de.exxe.AuctionHunter.GUI;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemManager {
	
	public static ItemStack prepareItem(String itemName, String name) {
		ItemStack item = new ItemStack(Material.getMaterial(itemName));
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}
	
	public static void increaseStart(Inventory gui) {
		ItemStack oldItem;
		ItemMeta meta;
		int startValue = 10;
		int Amount = 1;
		oldItem = gui.getItem(33);
		meta = oldItem.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7Startwert: &620$"));
		oldItem.setItemMeta(meta);
		oldItem.setAmount(oldItem.getAmount() + 1);
		
		oldItem = gui.getItem(24);
		meta = oldItem.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7Startwert: &620$ &8| &6\u25B2 &7Höher &6\u25B2"));
		oldItem.setItemMeta(meta);
		
		oldItem = gui.getItem(42);
		meta = oldItem.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7Startwert: &620$ &8| &6\u25BC &7Niedriger &6\u25BC"));
		oldItem.setItemMeta(meta);
	}
	
	public static void decreaseStart(Inventory gui) {
		ItemStack oldItem;
		ItemMeta meta;
		oldItem = gui.getItem(33);
		meta = oldItem.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7Startwert: &60$"));
		oldItem.setItemMeta(meta);
		oldItem.setAmount(oldItem.getAmount() - 1);
		
		oldItem = gui.getItem(24);
		meta = oldItem.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7Startwert: &60$ &8| &6\u25B2 &7Höher &6\u25B2"));
		oldItem.setItemMeta(meta);
		
		oldItem = gui.getItem(42);
		meta = oldItem.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7Startwert: &60$ &8| &6\u25BC &7Niedriger &6\u25BC"));
		oldItem.setItemMeta(meta);
	}

}
