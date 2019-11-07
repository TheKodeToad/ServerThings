package me.mcblueparrot.serverthings.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.mcblueparrot.serverthings.Main;
import me.mcblueparrot.serverthings.events.CommandTabCompleter;

public class ResourcePack implements CommandExecutor{
	
	private Main plugin;
	
	public ResourcePack(Main plugin) {
		this.plugin = plugin;
		this.plugin.getCommand("resourcepack").setExecutor(this);
		this.plugin.getCommand("resourcepack").setTabCompleter(new CommandTabCompleter(this.plugin));
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("serverthings.resourcepack")) {
			if(args.length == 0) {
				sender.sendMessage("§cChoose player or global");
				return true;
			}
			else if(args.length == 1) {
				if(args[0].equals("global")) {
					plugin.getConfig().set("resourcepack", null);
					plugin.saveConfig();
					sender.sendMessage("Global resource pack reset; players will have to relog");
				}
				else if(args[0].equals("player")) {
					sender.sendMessage("§cPlease choose a player");
				}
				else {
					sender.sendMessage("§cChoose player or global");
				}
				return true;
			}
			else if(args.length == 2 && args[0].equals("player")) {
				sender.sendMessage("§cPlease specify a web address");
				return true;
			}
			else if (args.length > 2 && args[0].equals("player")) {
				Player target = Bukkit.getServer().getPlayer(args[1]);
				if(!target.isOnline()) {
					sender.sendMessage("§c" + args[1] + " isn't online");
					return true;
				}
				target.setResourcePack(args[2]);
				sender.sendMessage("Set resource pack for player " + args[1]);
				return true;
			}
			else if (args.length > 1 && args[0].equals("global")) {
				plugin.getConfig().set("resourcepack", args[1]);
				plugin.saveConfig();
				sender.sendMessage("Set global resource pack");
				for (Player target : Bukkit.getServer().getOnlinePlayers()) {
					target.setResourcePack(args[1]);
				}
				return true;
			}
			return true;
		}
		else {
			sender.sendMessage("§cYou do not have permission");
			return true;
		}
	}

}
