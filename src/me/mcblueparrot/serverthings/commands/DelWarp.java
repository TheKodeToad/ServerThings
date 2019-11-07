package me.mcblueparrot.serverthings.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.mcblueparrot.serverthings.Main;
import me.mcblueparrot.serverthings.events.CommandTabCompleter;

public class DelWarp implements CommandExecutor {
	
	private Main plugin;
	
	public DelWarp(Main plugin) {
		this.plugin = plugin;
		this.plugin.getCommand("delwarp").setExecutor(this);
		this.plugin.getCommand("delwarp").setTabCompleter(new CommandTabCompleter(this.plugin));
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("serverthings.delwarp")) {
			plugin.reloadConfig();
			if(args.length == 0) {
				sender.sendMessage("Please specify a warp point to delete");
			}
			else {
				if(plugin.getConfig().getString("warps." + args[0]) == null) {
					sender.sendMessage("§cWarp point " + args[0] + " hasn't been created. Run /listwarps to see a list of warps or /makewarp to make a new one");
					return true;
				}
				else {
					plugin.getConfig().set("warps." + args[0], null);
					plugin.saveConfig();
					sender.sendMessage("Deleted warp point " + args[0]);
					return true;
				}
			}
		}
		else {
			sender.sendMessage("§cYou do not have permission");
			return true;
		}
		return true;
	}
	
}
