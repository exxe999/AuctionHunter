package de.exxe.AuctionHunter.GUI;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.exxe.AuctionHunter.Main.Main;

public class AuctionClaim {
	
	private Main main;
	boolean isIntended;

	public AuctionClaim(Main main) {
		this.main = main;
	}
	
	public void openAuctionClaim(Player player) {
		Inventory gui = Bukkit.createInventory(player, 27, ChatColor.translateAlternateColorCodes('&', "&8&lErsteigerte Items"));
		List<?> items = main.getCustomConfig().get().getList(player.getUniqueId() + "." + "auctionClaimItems");
		
		if(items == null) {
			player.sendMessage("Keine Items verfügbar!");
			return;
		}
		
		
		for(int i = 0; i < 27; i++) {
			gui.setItem(i, (ItemStack) items.get(i));
		}
		
		player.openInventory(gui);
	}
	
	public void updateAuctionClaim(Player player) {
		List<ItemStack> items = new ArrayList<ItemStack>();
		for(int i = 0; i < 27; i++) {
			items.add(player.getOpenInventory().getItem(i));
		}
		main.getCustomConfig().get().set(player.getUniqueId() + "." + "auctionChestItems", items);
		main.getCustomConfig().saveConfig();
		player.closeInventory();
	}

}
