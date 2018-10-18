package de.exxe.AuctionHunter.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.exxe.AuctionHunter.AuctionHandler.Auction;
import de.exxe.AuctionHunter.GUI.AuctionClaim;
import de.exxe.AuctionHunter.GUI.StartGUI;
import de.exxe.AuctionHunter.Main.Main;


public class Commands implements CommandExecutor {
	
	public Main main;
	public StartGUI startGUI;
	
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
					main.getCustomConfig().get().set(player.getUniqueId() + "." + "auctionStartValue", 0);
					main.getCustomConfig().saveConfig();
					new StartGUI(main).openStartGUI(player);
					return true;
				}
				else if(args[0].equalsIgnoreCase("info")) {
					sender.sendMessage("zeige info gui an");
					return true;
				}else if(args[0].equalsIgnoreCase("pause")) {
					sender.sendMessage("Pausiere Auktion");
					return true;
				}else if(args[0].equalsIgnoreCase("claim")){
					new AuctionClaim(main).openAuctionClaim(player);
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
				if(isNumeric(args[0])) main.getAuctionManager().getAuction().setHighestBid(player, Double.parseDouble(args[0]));
				else player.sendMessage("Du musst eine Zahl angeben.");
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
	
	public static boolean isNumeric(String strNum) {
	    try {
	        double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException | NullPointerException nfe) {
	        return false;
	    }
	    return true;
	}
	
}
