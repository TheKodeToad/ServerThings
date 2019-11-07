package me.mcblueparrot.serverthings.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.mcblueparrot.serverthings.Main;
import me.mcblueparrot.serverthings.events.CommandTabCompleter;

public class Warp implements CommandExecutor{
	
	private Main plugin;
	
	public Warp(Main plugin) {
		this.plugin = plugin;
		this.plugin.getCommand("warp").setExecutor(this);
		this.plugin.getCommand("warp").setTabCompleter(new CommandTabCompleter(this.plugin));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("serverthings.warp")) {
			plugin.reloadConfig();
			if(args.length == 1) {
				if(sender instanceof Player) {
					Player target = (Player) sender;
					if(plugin.getConfig().contains("warps." + args[0])) {
						sender.sendMessage("Warping to " + args[0]);
						Location location = new Location(Bukkit.getServer().getWorld(plugin.getConfig().getString("warps." + args[0]).split(" ")[3]), Double.parseDouble(plugin.getConfig().getString("warps." + args[0]).split(" ")[0]), Double.parseDouble(plugin.getConfig().getString("warps." + args[0]).split(" ")[1]), Double.parseDouble(plugin.getConfig().getString("warps." + args[0]).split(" ")[2]));
						location.setYaw(target.getLocation().getYaw());
						location.setPitch(target.getLocation().getPitch());
						target.teleport(location);
					}
					else {
						sender.sendMessage("§cWarp point " + args[0] + " hasn't been created. Run /listwarps to see a list of warps or /makewarp to make a new one");
					}
				}
				else {
					sender.sendMessage("§cPlease choose a player to warp");
				}
				return true;
			}
			else if(args.length == 2) {
				Player target = Bukkit.getServer().getPlayer(args[1]);
				if(target == null) {
					sender.sendMessage("§c" + args[1] + " isn't online");
					return true;
				}
				if(plugin.getConfig().contains("warps." + args[0])) {
					sender.sendMessage("Warping to " + args[0]);
					Location location = new Location(Bukkit.getServer().getWorld(plugin.getConfig().getString("warps." + args[0]).split(" ")[3]), Double.parseDouble(plugin.getConfig().getString("warps." + args[0]).split(" ")[0]), Double.parseDouble(plugin.getConfig().getString("warps." + args[0]).split(" ")[1]), Double.parseDouble(plugin.getConfig().getString("warps." + args[0]).split(" ")[2]));
					location.setYaw(target.getLocation().getYaw());
					location.setPitch(target.getLocation().getPitch());
					target.teleport(location);
				}
				else {
					sender.sendMessage("§cWarp point " + args[0] + " hasn't been created. Run /listwarps to see a list of warps or /makewarp to make a new one");
				}
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
