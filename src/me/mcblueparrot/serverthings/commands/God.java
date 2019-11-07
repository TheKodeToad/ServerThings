package me.mcblueparrot.serverthings.commands;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.mcblueparrot.serverthings.Main;
import me.mcblueparrot.serverthings.events.CommandTabCompleter;

public class God implements CommandExecutor{
	
	private Main plugin;
	public ArrayList<String> gods = new ArrayList<String>();
	
	public God(Main plugin) {
		this.plugin = plugin;
		this.plugin.getCommand("god").setExecutor(this);
		this.plugin.getCommand("god").setTabCompleter(new CommandTabCompleter(this.plugin));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		try {
		if(sender.hasPermission("serverthings.god")) {
			plugin.reloadConfig();
			if(!(sender instanceof Player)) {
				if(args.length == 0) {
					sender.sendMessage("§cPlease choose a player");
					return true;
				}
				else if(args.length == 1) {
					Player target = Bukkit.getServer().getPlayer(args[0]);
					if(target != null) {
						if(isGod(target)) {
							toggle(target, false);
							sender.sendMessage("Player " + target.getName() + " can now take damage");
							return true;
						}
						else {
							toggle(target, true);
							sender.sendMessage("Player " + target.getName() + " can no longer take damage");
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
					if(target == null) {
						sender.sendMessage("§c" + args[0] + " isn't online!");
						return false;
					}
					if(args[1].equalsIgnoreCase("on")) {
						toggle(target, true);
						sender.sendMessage("Player " + target.getName() + " can no longer take damage");
						return true;
					}
					else if(args[1].equalsIgnoreCase("off")) {
						toggle(target, false);
						sender.sendMessage("Player " + target.getName() + " can now take damage");
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
					if(isGod(target)) {
						toggle(target, false);
						sender.sendMessage("Player " + target.getName() + " can now take damage");
						return true;
					}
					else {
						toggle(target, true);
						sender.sendMessage("Player " + target.getName() + " can no longer take damage");
						return true;
					}
				}
				else if(args.length == 1) {
					Player target = Bukkit.getServer().getPlayer(args[0]);
					if(target != null) {
						if(isGod(target)) {
							toggle(target, false);
							sender.sendMessage("Player " + target.getName() + " can now take damage");
							return true;
						}
						else {
							toggle(target, true);
							sender.sendMessage("Player " + target.getName() + " can no longer take damage");
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
					if(args[1].equalsIgnoreCase("on")) {
						toggle(target, true);
						sender.sendMessage("Player " + target.getName() + " can no longer take damage");
						return true;
					}
					else if(args[1].equalsIgnoreCase("off")) {
						toggle(target, false);
						sender.sendMessage("Player " + target.getName() + " can now take damage");
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
	
	private boolean isGod(Player target) {
		return gods.contains(target.getName());
	}
	
	private void toggle(Player target, boolean toggle) {
		if(toggle) {
			if(!gods.contains(target.getName())) {
				gods.add(target.getName());
			}
		}
		else {
			if(gods.contains(target.getName())) {
				gods.remove(target.getName());
			}
		}
		plugin.getConfig().set("god." + target.getName(), Boolean.toString(toggle));
		plugin.saveConfig();
	}

}
