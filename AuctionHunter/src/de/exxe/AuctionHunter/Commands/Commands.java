package de.exxe.AuctionHunter.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.exxe.AuctionHunter.GUI.AuctionGUI;
import de.exxe.AuctionHunter.Main.Main;


public class Commands implements CommandExecutor {
	
	public Main main;
	public AuctionGUI auctionGUI;
	
	public Commands(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLable, String[] args) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&',"&7[&cAuctionHunter&7] Du bist kein Spieler!"));
			return true;
		}
		
		Player player = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("auction")) {
			if(args.length >= 1) {
				if(args[0].equalsIgnoreCase("start")) {
					sender.sendMessage("starte GUI");
					new AuctionGUI(main).openAuctionGUI(player);
					return true;
				}
				else if(args[0].equalsIgnoreCase("info")) {
					sender.sendMessage("zeige info gui an");
					return true;
				}else {
					sender.sendMessage("/auktion");
					return true;
				}
			}else {
				sender.sendMessage("/auktion");
				return true;
			}
		}
		else if(cmd.getName().equalsIgnoreCase("bid")) {
			if(args.length == 1) {
				sender.sendMessage("starte gebot");
				//start bid
				return true;
			}else {
				sender.sendMessage("fehler");
				return true;
			}
		}else {
			sender.sendMessage("plugin funktion");
			return true;
		}
	
	}
	
}
