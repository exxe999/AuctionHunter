package de.exxe.AuctionHunter.GUI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.exxe.AuctionHunter.Main.Main;

public class StartGUI {
	
	private Main main;
	private static ItemStack auctionChestItem;
	private ItemStack goldItem;
	private ItemStack seperateItem = ItemManager.prepareItem("STAINED_GLASS_PANE"," ");
	private static ItemStack startItem;
	private ItemStack buttonUp;
	private ItemStack buttonDown;
	private Player player;
	private int startValue;
	public StartGUI(Main main) {
		this.main = main;
	}
	
	public void openStartGUI(Player player) {
		this.player = player;
		boolean hasAuctionChest = false;
		hasAuctionChest = new AuctionChest(main).isEmpty(player);
		
		Inventory gui = Bukkit.createInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&8&lAuktion"));
		
		if(hasAuctionChest) {
			prepareGeneralGUI();
		}else {
			prepareCustomGUI();
		}
		
		updateStartValueItems();
		seperateItem.setDurability((short) 7);
		gui.setItem(0, auctionChestItem);
		gui.setItem(24, buttonUp);
		gui.setItem(33, goldItem);
		gui.setItem(42, buttonDown);
		gui.setItem(29, startItem);
		gui.setItem(30, startItem);
		gui.setItem(38, startItem);
		gui.setItem(39, startItem);
		for(int i = 9;i <18;i++) {
			gui.setItem(i, seperateItem);
		}
		player.openInventory(gui);
	}

	private void prepareGeneralGUI() {
		startValue = main.getCustomConfig().get().getInt("auctionStartValue" + "." + player.getUniqueId());
		auctionChestItem = ItemManager.prepareItem("CHEST", ChatColor.translateAlternateColorCodes('&', "&aNeue Auktionskiste"));
		startItem = ItemManager.prepareItem("STAINED_GLASS_PANE",ChatColor.translateAlternateColorCodes('&', "&cErstelle eine Auktionskiste!"));
		startItem.setDurability((short) 14);	
	}
	
	private void prepareCustomGUI() {
		startValue = main.getCustomConfig().get().getInt("auctionStartValue" + "." + player.getUniqueId());
		auctionChestItem = ItemManager.prepareItem("CHEST", ChatColor.translateAlternateColorCodes('&', "&aDeine Auktionskiste"));
		auctionChestItem = ItemManager.setItemMeta(auctionChestItem, "Items gespeichert.");
		startItem = ItemManager.prepareItem("STAINED_GLASS_PANE",ChatColor.translateAlternateColorCodes('&', "&a&lStarte Auktion"));
		startItem.setDurability((short) 5);
	}
	
	private void updateStartValueItems() {
		if(startValue <= 30 && startValue >= 0) {
			buttonUp = ItemManager.prepareItem("STONE_BUTTON",ChatColor.translateAlternateColorCodes('&', "&7Startwert: &6" + startValue * 10 + "$ &8| &6\u25B2 &7Höher &6\u25B2"));
			buttonDown = ItemManager.prepareItem("WOOD_BUTTON",ChatColor.translateAlternateColorCodes('&', "&7Startwert: &6" + startValue * 10 + "$ &8| &6\u25BC &7Niedriger &6\u25BC"));
			goldItem = ItemManager.prepareItem("GOLD_INGOT",ChatColor.translateAlternateColorCodes('&', "&7Startwert: &6" + startValue * 10 + "$"));
		}
		
		if(startValue != 0) {
			goldItem.setAmount(startValue);
		}else {
			ItemMeta meta = goldItem.getItemMeta();
			goldItem.setAmount(1);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
			goldItem.setItemMeta(meta);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
