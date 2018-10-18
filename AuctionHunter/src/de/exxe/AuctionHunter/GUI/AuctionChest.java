package de.exxe.AuctionHunter.GUI;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.exxe.AuctionHunter.Main.Main;

public class AuctionChest {
	
	private Main main;
	boolean isIntended;

	public AuctionChest(Main main) {
		this.main = main;
	}
	
	public void openAuctionChest(Player player) {
		
		Inventory gui = Bukkit.createInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&8&lDeine Auktionskiste"));
		ItemStack seperateItem = ItemManager.prepareItem("STAINED_GLASS_PANE"," ");
		ItemStack saveItem = ItemManager.prepareItem("STAINED_GLASS_PANE", ChatColor.translateAlternateColorCodes('&', "&aSpeichern"));
		ItemStack abortItem = ItemManager.prepareItem("STAINED_GLASS_PANE", ChatColor.translateAlternateColorCodes('&', "&cLöschen"));
		saveItem.setDurability((short) 5);
		abortItem.setDurability((short) 14);
		seperateItem.setDurability((short) 7);
		for(int i = 27;i <45;i++) {
			if(!(i == 42 || i == 38)) {
				gui.setItem(i, seperateItem);
			}
		}
		gui.setItem(42, saveItem);
		gui.setItem(38, abortItem);
		
		List<?> items = main.getCustomConfig().get().getList( player.getUniqueId() + "." + "auctionChestItems");
		if(items != null) {
			for(int i = 0; i < 27; i++) {
				gui.setItem(i, (ItemStack) items.get(i));
			}
		}
		
		
		
		player.openInventory(gui);
		
	}
	
	public boolean isEmpty(Player player) {
		List<?> items = main.getCustomConfig().get().getList(player.getUniqueId() + "." + "auctionChestItems");
		if(items != null) {
			for(Object o : items) {
				ItemStack item = (ItemStack) o;
				if(!(item.getType().equals(Material.AIR))){
					return false;
				}
			}
		}
		return true;
	}
	
	public void deleteAuctionChest(Player player) {
		//abbort in Auktionskiste
		isIntended = true;
		for(int i = 0;  i < 27;i++) {
			ItemStack item = player.getOpenInventory().getItem(i);
			if(!(item == null)) {
				for(int j = 0; j < 36;j++) {
					if(player.getInventory().getItem(j) == null) {
						player.getInventory().setItem(j,item);
						break;
					}
				}
			}
		}
		main.getCustomConfig().get().set(player.getUniqueId() + "." + "auctionChestItems", null);
		main.getCustomConfig().saveConfig();
		player.closeInventory();
		isIntended = false;
	}
	
	public void safeAuctionChest(Player player) {
		isIntended = true;
		List<ItemStack> items = new ArrayList<ItemStack>();
		for(int i = 0; i < 27; i++) {
			items.add(player.getOpenInventory().getItem(i));
		}
		main.getCustomConfig().get().set(player.getUniqueId() + "." + "auctionChestItems", items);
		main.getCustomConfig().saveConfig();
		player.closeInventory();
		isIntended = false;
	}
	
	public void forceSafeAuctionChest(Player player) {
		if(isIntended) return;
		if(isEmpty(player)) return;
		List<ItemStack> items = new ArrayList<ItemStack>();
		for(int i = 0; i < 27; i++) {
			items.add(player.getOpenInventory().getItem(i));
		}
		main.getCustomConfig().get().set(player.getUniqueId() + "." + "auctionChestItems", items);
		main.getCustomConfig().saveConfig();
		player.sendMessage("Speichere deine Auktionskiste vorher!");
	}

}
