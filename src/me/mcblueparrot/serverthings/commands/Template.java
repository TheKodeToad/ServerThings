package me.mcblueparrot.serverthings.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.mcblueparrot.serverthings.Main;

public class Template implements CommandExecutor{
	
	private Main plugin;
	
	public Template(Main plugin) {
		this.plugin = plugin;
		this.plugin.getCommand("template").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("serverthings.template")) {
			//Do Things
			return true;
		}
		else {
			sender.sendMessage("§cYou do no have permission");
			return true;
		}
	}
	
}
