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

public class Pvp implements CommandExecutor{
	
	private Main plugin;
	
	public Pvp(Main plugin) {
		this.plugin = plugin;
		this.plugin.getCommand("pvp").setExecutor(this);
		this.plugin.getCommand("pvp").setTabCompleter(new CommandTabCompleter(this.plugin));
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("serverthings.pvp")) {
			if(args.length == 0) {
				try {
		        	File properties = new File(plugin.getDataFolder().getParentFile().getParentFile(), "server.properties");
		            Properties propertiesedit = new Properties();
		            FileReader propertiesread = new FileReader(properties);
		            propertiesedit.load(propertiesread);
		            String pvp = propertiesedit.getProperty("pvp");
		            propertiesread.close();
		            sender.sendMessage("Pvp is " + (pvp.equals("true") ? "on" : "off"));
		        }
		        catch (Exception e) {
		            e.printStackTrace();
		        }
				
			}
			else if(args.length > 0) {
				if(!(args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("off"))){
					sender.sendMessage("Please turn pvp on or off");
					return true;
				}
				String pvpValue = (args[0].equals("on") ? "true" : "false");
		        try {
		        	File properties = new File(plugin.getDataFolder().getParentFile().getParentFile(), "server.properties");
		            Properties propertiesedit = new Properties();
		            FileReader propertiesread = new FileReader(properties);
		            propertiesedit.load(propertiesread);
		            propertiesedit.setProperty("pvp", pvpValue);
		            propertiesedit.store(new FileWriter(properties), "Minecraft server properties");
		            propertiesread.close();
		        	
		            for(World world : Bukkit.getServer().getWorlds()) {
		            	if(args[0].equalsIgnoreCase("on")){
		            		world.setPVP(true);
		            	}
		            	else if(args[0].equalsIgnoreCase("off")) {
		            		world.setPVP(false);
		            	}
		            }
		            sender.sendMessage("Pvp is now " + args[0].toLowerCase());
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
