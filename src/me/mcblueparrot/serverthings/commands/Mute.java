package me.mcblueparrot.serverthings.commands;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import me.mcblueparrot.serverthings.Main;
import me.mcblueparrot.serverthings.events.CommandTabCompleter;

public class Mute implements CommandExecutor{
	
	private Main plugin;
	public ArrayList<String> mutes = new ArrayList<String>();
	
	public Mute(Main plugin) {
		this.plugin = plugin;
		this.plugin.getCommand("mute").setExecutor(this);
		this.plugin.getCommand("mute").setTabCompleter(new CommandTabCompleter(this.plugin));
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("serverthings.mute")) {
			if(args.length == 0) {
				sender.sendMessage("§cPlease choose a player to mute");
				return true;
			}
			else if(args.length > 0) {
				if(mutes.contains(args[0])) {
					sender.sendMessage(args[0] + " is already muted!");
					return true;
				}
				mutes.add(args[0]);
				sender.sendMessage("Muted " + args[0]);
			}
			return true;
		}
		else {
			sender.sendMessage("§cYou do not have permission");
			return true;
		}
	}

}
