package me.mcblueparrot.serverthings.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.mcblueparrot.serverthings.Main;
import me.mcblueparrot.serverthings.events.CommandTabCompleter;

public class Workbench implements CommandExecutor {
	
	private Main plugin;
	
	public Workbench(Main plugin) {
		this.plugin = plugin;
		this.plugin.getCommand("workbench").setExecutor(this);
		this.plugin.getCommand("workbench").setTabCompleter(new CommandTabCompleter(this.plugin));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("serverthings.workbench")) {
			if(sender instanceof Player) {
				Player target = (Player) sender;
				target.openWorkbench(null, true);
				return true;
			}
			else {
				sender.sendMessage("§cYou are not a player");
				return true;
			}
			
		}
		else {
			sender.sendMessage("§cYou do no have permission");
			return true;
		}
	}

}
