package me.mcblueparrot.serverthings.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.mcblueparrot.serverthings.Main;
import me.mcblueparrot.serverthings.events.CommandTabCompleter;

public class MakeWarp implements CommandExecutor{
	
	private Main plugin;
	
	public MakeWarp(Main plugin) {
		this.plugin = plugin;
		this.plugin.getCommand("makewarp").setExecutor(this);
		this.plugin.getCommand("makewarp").setTabCompleter(new CommandTabCompleter(plugin));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("serverthings.makewarp")) {
			plugin.reloadConfig();
			if(args.length == 0) {
				sender.sendMessage("§cPlease specify a name for the warp point");
				return true;
			}
			else if(args.length == 1) {
				if(sender instanceof Player) {
					if(args[0].contains("\"") || args[0].contains(":") || args[0].contains(";") || args[0].contains("'")) {
						sender.sendMessage("§cThe name cannot contain a \":\", \";\" or any quotation mark.");
						return true;
					}
					Player target = (Player) sender;
					int xflat = (int) target.getLocation().getX();
					float x = (float) (xflat + 0.5);
					int y = (int) target.getLocation().getY();
					int zflat = (int) target.getLocation().getZ();
					float z = (float) (zflat + 0.5);
					String xs = Float.toString(x);
					String ys = Float.toString(y);
					String zs = Float.toString(z);
					plugin.getConfig().set("warps." + args[0], xs + " " + ys + " " + zs + " " + target.getWorld().getName());
					plugin.saveConfig();
					sender.sendMessage("Warp point " + args[0] + " has been created");
				}
				else {
					sender.sendMessage("§cPlease specify the location of the warp point");
				}
				return true;
			}
			else if(args.length == 4) {
				if(args[0].contains("\"") || args[0].contains(":") || args[0].contains(";") || args[0].contains("'")) {
					sender.sendMessage("§cThe name cannot contain a \":\", \";\" or any quotation mark.");
					return true;
				}
				String xs = args[1];
				String ys = args[2];
				String zs = args[3];
				
				try {
					Double.parseDouble(xs);
				}
				catch(Exception e){
					sender.sendMessage("Please use valid numbers");
					return true;
				}
				
				try {
					Double.parseDouble(ys);
				}
				catch(Exception e){
					sender.sendMessage("Please use valid numbers");
					return true;
				}
				
				try {
					Double.parseDouble(zs);
				}
				catch(Exception e){
					sender.sendMessage("Please use valid numbers");
					return true;
				}
				
				if(Bukkit.getWorld("world") == null) {
					sender.sendMessage("§cVanilla overworld not found - please specify world name");
					return true;
				}
				
				plugin.getConfig().set("warps." + args[0], xs + " " + ys + " " + zs + " " + Bukkit.getServer().getWorld("world").getName());
				plugin.saveConfig();
				sender.sendMessage("Warp point " + args[0] + " has been created");
				return true;
			}
			else if(args.length == 5) {
				if(args[0].contains("\"") || args[0].contains(":") || args[0].contains(";") || args[0].contains("'")) {
					sender.sendMessage("§cThe name cannot contain a \":\", \";\" or any quotation mark.");
					return true;
				}
				String xs = args[1];
				String ys = args[2];
				String zs = args[3];
				
				try {
					Double.parseDouble(xs);
				}
				catch(Exception e){
					sender.sendMessage("Please use valid numbers");
					return true;
				}
				
				try {
					Double.parseDouble(ys);
				}
				catch(Exception e){
					sender.sendMessage("Please use valid numbers");
					return true;
				}
				
				try {
					Double.parseDouble(zs);
				}
				catch(Exception e){
					sender.sendMessage("Please use valid numbers");
					return true;
				}
				
				if(Bukkit.getWorld(args[4]) == null) {
					sender.sendMessage("§cWorld " + args[4] + " not found");
					return true;
				}
				
				plugin.getConfig().set("warps." + args[0], xs + " " + ys + " " + zs + " " + Bukkit.getServer().getWorld(args[4]).getName());
				plugin.saveConfig();
				sender.sendMessage("Warp point " + args[0] + " has been created");
				return true;
			}
		}
		else {
			sender.sendMessage("§cYou do not have permission");
			return true;
		}
		return false;
	}

}
