package me.mcblueparrot.serverthings.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.mcblueparrot.serverthings.Main;
import me.mcblueparrot.serverthings.events.CommandTabCompleter;

public class Fly implements CommandExecutor{
	
	private Main plugin;
	
	public Fly(Main plugin) {
		this.plugin = plugin;
		this.plugin.getCommand("fly").setExecutor(this);
		this.plugin.getCommand("fly").setTabCompleter(new CommandTabCompleter(this.plugin));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		try {
		if(sender.hasPermission("serverthings.fly")) {
			plugin.reloadConfig();
			if(!(sender instanceof Player)) {
				if(args.length == 0) {
					sender.sendMessage("§cPlease choose a player");
					return true;
				}
				else if(args.length == 1) {
					Player target = Bukkit.getServer().getPlayer(args[0]);
					if(target.isOnline()) {
						if(target.getAllowFlight()) {
							toggle(target, false);
							sender.sendMessage("Player " + target.getName() + " can no longer fly");
							return true;
						}
						else {
							toggle(target, true);
							sender.sendMessage("Player " + target.getName() + " can now fly");
							return true;
						}
					}
					else {
						sender.sendMessage("§c" + args[0] + " isn't online!");
						return true;
					}
				}
				else if(args.length == 2 || args.length > 1) {
					Player target = Bukkit.getServer().getPlayer(args[0]);
					if(!target.isOnline()) {
						sender.sendMessage("§c" + args[0] + " isn't online!");
						return true;
					}
					if(args[1].equalsIgnoreCase("can")) {
						toggle(target, true);
						sender.sendMessage("Player " + target.getName() + " can now fly");
						return true;
					}
					else if(args[1].equalsIgnoreCase("cannot")) {
						toggle(target, false);
						sender.sendMessage("Player " + target.getName() + " can no longer fly");
						return true;
						
					}
					else {
						return false;
					}
				}
			}
			else if(sender instanceof Player) {
				if(args.length == 0) {
					Player target = (Player) sender;
					if(target.getAllowFlight()) {
						toggle(target, false);
						sender.sendMessage("Player " + target.getName() + " can no longer fly");
						return true;
					}
					else {
						toggle(target, true);
						sender.sendMessage("Player " + target.getName() + " can now fly");
						return true;
					}
				}
				else if(args.length == 1) {
					Player target = Bukkit.getServer().getPlayer(args[0]);
					if(target != null) {
						if(target.getAllowFlight()) {
							toggle(target, false);
							sender.sendMessage("Player " + target.getName() + " can no longer fly");
							return true;
						}
						else {
							toggle(target, true);
							sender.sendMessage("Player " + target.getName() + " can now fly");
							return true;
						}
					}
					else {
						sender.sendMessage("§cPlayer " + args[0] + " cannot be found");
						return true;
					}
				}
				else if(args.length == 2 || args.length > 1) {
					Player target = Bukkit.getServer().getPlayer(args[0]);
					if(args[1].equalsIgnoreCase("can")) {
						toggle(target, true);
						sender.sendMessage("Player " + target.getName() + " can now fly");
						return true;
					}
					else if(args[1].equalsIgnoreCase("cannot")) {
						toggle(target, false);
						sender.sendMessage("Player " + target.getName() + " can no longer fly");
						return true;
						
					}
					else {
						return false;
					}
				}
			}
		}
		
		else {
			sender.sendMessage("§cYou do not have permission");
			return true;
		}
		}
		catch(Exception e) {
			sender.sendMessage("§c" + args[0] + " isn't online!");
			return true;
		}
		return false;
	}
	
	private void toggle(Player target, boolean toggle) {
		target.setAllowFlight(toggle);
		if(plugin.getConfig().getString("saveflight").equals("true")) {
			plugin.getConfig().set("flight." + target.getName(), Boolean.toString(toggle));
			plugin.saveConfig();
		}
	}

}
