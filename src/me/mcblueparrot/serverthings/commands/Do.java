package me.mcblueparrot.serverthings.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.mcblueparrot.serverthings.Main;
import me.mcblueparrot.serverthings.events.CommandTabCompleter;
import me.mcblueparrot.serverthings.utils.Utils;

public class Do implements CommandExecutor{
	
	private Main plugin;
	
	public Do(Main plugin) {
		this.plugin = plugin;
		this.plugin.getCommand("do").setExecutor(this);
		this.plugin.getCommand("do").setTabCompleter(new CommandTabCompleter(this.plugin));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("serverthings.do")) {
			if(args.length > 0) {
				String argsJoin = Utils.joinArgs(args);
				String[] argsSplit = argsJoin.split(" \\/then ");
				for(String arg : argsSplit) {
					Bukkit.getServer().dispatchCommand(sender, arg);
				}
				return true;
			}
			else {
				return false;
			}
		}
		else {
			sender.sendMessage("§cYou do no have permission");
			return true;
		}
	}
	
}
