package me.mcblueparrot.serverthings.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import me.mcblueparrot.serverthings.Main;
import me.mcblueparrot.serverthings.events.CommandTabCompleter;

public class Unmute implements CommandExecutor{
	
	private Main plugin;
	
	public Unmute(Main plugin) {
		this.plugin = plugin;
		this.plugin.getCommand("unmute").setExecutor(this);
		this.plugin.getCommand("unmute").setTabCompleter(new CommandTabCompleter(this.plugin));
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("serverthings.unmute")) {
			if(args.length == 0) {
				sender.sendMessage("§cPlease choose a player to unmute");
				return true;
			}
			else if(args.length > 0) {
				if(!plugin.mutecommand.mutes.contains(args[0])) {
					sender.sendMessage(args[0] + " isn't muted");
					return true;
				}
				plugin.mutecommand.mutes.remove(args[0]);
				sender.sendMessage("Unmuted " + args[0]);
			}
			return true;
		}
		else {
			sender.sendMessage("§cYou do not have permission");
			return true;
		}
	}

}
