package me.mcblueparrot.serverthings.commands;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.mcblueparrot.serverthings.Main;
import me.mcblueparrot.serverthings.events.CommandTabCompleter;
import me.mcblueparrot.serverthings.utils.Utils;

public class Difficulty implements CommandExecutor{
	
	private Main plugin;
	
	public Difficulty(Main plugin) {
		this.plugin = plugin;
		this.plugin.getCommand("difficulty").setExecutor(this);
		this.plugin.getCommand("difficulty").setTabCompleter(new CommandTabCompleter(this.plugin));
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("serverthings.difficulty")) {
			if(args.length == 0) {
				try {
		        	File properties = new File(plugin.getDataFolder().getParentFile().getParentFile(), "server.properties");
		            Properties propertiesedit = new Properties();
		            FileReader propertiesread = new FileReader(properties);
		            propertiesedit.load(propertiesread);
		            String difficulty = propertiesedit.getProperty("difficulty");
		            propertiesread.close();
		            sender.sendMessage("The difficulty is " + Utils.capitalizeFirstLetter(difficulty));
		        }
		        catch (Exception e) {
		            e.printStackTrace();
		        }
				
			}
			else if(args.length > 0) {
				if(!(args[0].equalsIgnoreCase("easy") || args[0].equalsIgnoreCase("normal") || args[0].equalsIgnoreCase("hard") || args[0].equalsIgnoreCase("peaceful"))){
					sender.sendMessage("Please use a valid difficulty level - Peaceful, Easy, Normal or Hard");
					return true;
				}
		        try {
		        	File properties = new File(plugin.getDataFolder().getParentFile().getParentFile(), "server.properties");
		            Properties propertiesedit = new Properties();
		            FileReader propertiesread = new FileReader(properties);
		            propertiesedit.load(propertiesread);
		            propertiesedit.setProperty("difficulty", args[0].toLowerCase());
		            propertiesedit.store(new FileWriter(properties), "Minecraft server properties");
		            propertiesread.close();
		        	
		            for(World world : Bukkit.getServer().getWorlds()) {
		            	if(args[0].equalsIgnoreCase("easy")){
		            		world.setDifficulty(org.bukkit.Difficulty.EASY);
		            	}
		            	else if(args[0].equalsIgnoreCase("normal")) {
		            		world.setDifficulty(org.bukkit.Difficulty.NORMAL);
		            	}
		            	else if(args[0].equalsIgnoreCase("hard")) {
		            		world.setDifficulty(org.bukkit.Difficulty.HARD);
		            	}
		            	else if(args[0].equalsIgnoreCase("peaceful")) {
		            		world.setDifficulty(org.bukkit.Difficulty.PEACEFUL);
		            	}
		            }
		            sender.sendMessage("The difficulty has been set to " + Utils.capitalizeFirstLetter(args[0].toLowerCase()));
		        }
		        catch (Exception e) {
		            e.printStackTrace();
		        }
			}
			return true;
		}
		else {
			sender.sendMessage("§cYou do not have permission");
			return true;
		}
	}

}
