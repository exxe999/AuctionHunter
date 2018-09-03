package de.exxe.AuctionHunter.GUI;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.exxe.AuctionHunter.Main.Main;

public class ItemManager {
	
	private Main main;
	private int startValue;
	
	public ItemManager(Main main) {
		this.main = main;
	}
	
	public static ItemStack prepareItem(String itemName, String name) {
		ItemStack item = new ItemStack(Material.getMaterial(itemName));
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack setItemMeta(ItemStack item, String lore) {
		ItemMeta meta = item.getItemMeta();
		List<String> loreAsList = new ArrayList<String>();
		loreAsList.add(lore);
		meta.setLore(loreAsList);
		item.setItemMeta(meta);
		return item;
	}
	
	public void increaseStart(Inventory gui) {
		ItemStack oldItem;
		ItemMeta meta;
		Player player = (Player) gui.getHolder();
		startValue = main.getCustomConfig().get().getInt("auctionStartValue" + "." + player.getUniqueId());
		if(startValue < 30) {
			main.getCustomConfig().get().set("auctionStartValue" + "." + player.getUniqueId(), ++startValue);
		}
		oldItem = gui.getItem(33);
		meta = oldItem.getItemMeta();
		if(meta.hasEnchants()) meta.removeEnchant(Enchantment.KNOCKBACK);
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7Startwert: &6" + startValue * 10 + "$"));
		oldItem.setItemMeta(meta);
		oldItem.setAmount(startValue);
		
		oldItem = gui.getItem(24);
		meta = oldItem.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7Startwert: &6" + startValue * 10 + "$ &8| &6\u25B2 &7Höher &6\u25B2"));
		oldItem.setItemMeta(meta);
		
		oldItem = gui.getItem(42);
		meta = oldItem.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7Startwert: &6" + startValue * 10 + "$ &8| &6\u25BC &7Niedriger &6\u25BC"));
		oldItem.setItemMeta(meta);
	}
	
	public void decreaseStart(Inventory gui) {
		ItemStack oldItem;
		ItemMeta meta;
		Player player = (Player) gui.getHolder();
		startValue = main.getCustomConfig().get().getInt("auctionStartValue" + "." + player.getUniqueId());
		if(startValue > 0) {
			main.getCustomConfig().get().set("auctionStartValue" + "." + player.getUniqueId(), --startValue);
		}
		oldItem = gui.getItem(33);
		meta = oldItem.getItemMeta();
		if(meta.hasEnchants()) meta.removeEnchant(Enchantment.KNOCKBACK);
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7Startwert: &6" + startValue * 10 + "$"));
		if(startValue != 0) {
			oldItem.setAmount(startValue);
		}else {
			oldItem.setAmount(1);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
		}
		oldItem.setItemMeta(meta);
		
		
		oldItem = gui.getItem(24);
		meta = oldItem.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7Startwert: &6" + startValue * 10 + "$ &8| &6\u25B2 &7Höher &6\u25B2"));
		oldItem.setItemMeta(meta);
		
		oldItem = gui.getItem(42);
		meta = oldItem.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7Startwert: &6" + startValue * 10 + "$ &8| &6\u25BC &7Niedriger &6\u25BC"));
		oldItem.setItemMeta(meta);
	}

}
