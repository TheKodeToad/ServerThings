package me.mcblueparrot.serverthings.commands;

import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.mcblueparrot.serverthings.Main;
import me.mcblueparrot.serverthings.events.CommandTabCompleter;

public class ListWarps implements CommandExecutor{
	
	private Main plugin;
	
	public ListWarps(Main plugin) {
		this.plugin = plugin;
		this.plugin.getCommand("listwarps").setExecutor(this);
		this.plugin.getCommand("listwarps").setTabCompleter(new CommandTabCompleter(this.plugin));
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("serverthings.listwarps")) {
			plugin.reloadConfig();
			Set<String> warps = plugin.getConfig().getConfigurationSection("warps").getKeys(true);
			for(Object name : warps) {
				String keyname = (String) name;
				sender.sendMessage(keyname + ": " + plugin.getConfig().getString("warps." + keyname));
			}
			return true;
		}
		else {
			sender.sendMessage("§cYou do not have permission");
			return true;
		}
	}

}
