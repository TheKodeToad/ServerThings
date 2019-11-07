package me.mcblueparrot.serverthings.commands;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.mcblueparrot.serverthings.Main;
import me.mcblueparrot.serverthings.events.CommandTabCompleter;
import me.mcblueparrot.serverthings.utils.Utils;

public class ChatFlilter implements CommandExecutor {
	
	private Main plugin;
	
	public ChatFlilter(Main plugin) {
		this.plugin = plugin;
		this.plugin.getCommand("chatfilter").setExecutor(this);
		this.plugin.getCommand("chatfilter").setTabCompleter(new CommandTabCompleter(this.plugin));
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("serverthings.pvp")) {
			if(args.length == 0) {
				sender.sendMessage("§cPlease choose an option: disable, enable, settings, addword (Add a word spelt backwards), removeword (Remove a word spelt backwards)");
			}
			else if(args.length == 1) {
				if(args[0].equalsIgnoreCase("disable")) {
					plugin.getConfig().set("chat.filter", "false");
					plugin.saveConfig();
					sender.sendMessage("Disabled chat filter");
				}
				else if(args[0].equalsIgnoreCase("enable")) {
					plugin.getConfig().set("chat.filter", "true");
					plugin.saveConfig();
					sender.sendMessage("Enabled chat filter");
				}
				else if(args[0].equalsIgnoreCase("settings")) {
					sender.sendMessage("§cPlease choose what to change: kickplayers (Kick players who send filtered messages), alertplayers (Send the username of the player whose message was filtered out to the server), showplayermessage (Show the message sent by that player)");
				}
				else if(args[0].equalsIgnoreCase("addword")) {
					sender.sendMessage("§cPlease choose a word to add to the filter (Spelt backwards)");
				}
				else if(args[0].equalsIgnoreCase("removeword")) {
					sender.sendMessage("§cPlease choose a word to remove from the filter (Spelt backwards)");
				}
				else {
					sender.sendMessage("§cPlease choose a valid option: disable, enable, settings, addword (Add a word spelt backwards), removeword (Remove a word spelt backwards)");
				}
				return true;

			}
			else if(args.length > 1 && args[0].equalsIgnoreCase("addword")) {
				String rudewords = plugin.getConfig().getString("rudewords");
				String[] rudewordslist = rudewords.split(",");
				ArrayList<String> rudewordsarraylist = new ArrayList<String>(Arrays.asList(rudewordslist));
				if(rudewordsarraylist.contains(args[1].toLowerCase())) {
					sender.sendMessage("§cThat word is already on the list");
					return true;
				}
				rudewordsarraylist.add(args[1].toLowerCase());
				plugin.getConfig().set("rudewords", Utils.joinArrayListWith(rudewordsarraylist, ","));
				plugin.saveConfig();
				sender.sendMessage("Added word");
			}
			else if(args.length > 1 && args[0].equalsIgnoreCase("removeword")) {
				String rudewords = plugin.getConfig().getString("rudewords");
				String[] rudewordslist = rudewords.split(",");
				ArrayList<String> rudewordsarraylist = new ArrayList<String>(Arrays.asList(rudewordslist));
				if(!rudewordsarraylist.contains(args[1].toLowerCase())) {
					sender.sendMessage("§cThat word isn't on the list");
					return true;
				}
				rudewordsarraylist.remove(rudewordsarraylist.indexOf(args[1].toLowerCase()));
				plugin.getConfig().set("rudewords", Utils.joinArrayListWith(rudewordsarraylist, ","));
				plugin.saveConfig();
				sender.sendMessage("Removed word");
			}
			else if(args.length == 2 && args[0].equals("settings")) {
				String setting = "none";
				if(args[1].equals("kickplayers")) {
					setting = "rudekick";
				}
				else if(args[1].equals("alertplayers")) {
					setting = "alertrude";
				}
				else if(args[1].equals("showplayermessage")) {
					setting = "showrudemessage";
				}
				if(setting.equals("none")) {
					sender.sendMessage("§cPlease a valid setting: kickplayers (Kick players who send filtered messages), alertplayers (Send the username of the player whose message was filtered out to the server), showplayermessage (Show the message sent by that player)");
					return true;
				}
				sender.sendMessage("§cPlease choose true or false for the setting");
				return true;
			}
			else if(args.length > 2 && args[0].equals("settings")) {
				if(!(args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("false"))) {
					sender.sendMessage("§cPlease choose true or false for the setting");
					return true;
				}
				String setting = "none";
				if(args[1].equals("kickplayers")) {
					setting = "rudekick";
				}
				else if(args[1].equals("alertplayers")) {
					setting = "alertrude";
				}
				else if(args[1].equals("showplayermessage")) {
					setting = "showrudemessage";
				}
				if(setting.equals("none")) {
					sender.sendMessage("§cPlease a valid setting: kickplayers (Kick players who send filtered messages), alertplayers (Send the username of the player whose message was filtered out to the server), showplayermessage (Show the message sent by that player)");
					return true;
				}
				plugin.getConfig().set("chat." + setting, args[2].toLowerCase());
				plugin.saveConfig();
				sender.sendMessage("Changed setting " + args[1] + " to true");
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
