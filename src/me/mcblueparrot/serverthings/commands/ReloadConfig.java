package me.mcblueparrot.serverthings.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.mcblueparrot.serverthings.Main;
import me.mcblueparrot.serverthings.events.CommandTabCompleter;

public class ReloadConfig implements CommandExecutor{
	
	private Main plugin;
	
	public ReloadConfig(Main plugin) {
		this.plugin = plugin;
		this.plugin.getCommand("serverthingsreloadconfig").setExecutor(this);
		this.plugin.getCommand("serverthingsreloadconfig").setTabCompleter(new CommandTabCompleter(plugin));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("serverthings.reloadconfig")) {
			plugin.reloadConfig();
			sender.sendMessage("Reloaded config");
			return true;
		}
		else {
			sender.sendMessage("§cYou do no have permission");
			return true;
		}
	}
	
}
