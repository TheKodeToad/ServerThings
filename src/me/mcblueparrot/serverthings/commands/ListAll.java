package me.mcblueparrot.serverthings.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;

import me.mcblueparrot.serverthings.Main;
import me.mcblueparrot.serverthings.events.CommandTabCompleter;
import me.mcblueparrot.serverthings.utils.MinecraftUtils;

public class ListAll implements CommandExecutor {
	
	private Main plugin;
	
	public ListAll(Main plugin) {
		this.plugin = plugin;
		this.plugin.getCommand("listall").setExecutor(this);
		this.plugin.getCommand("listall").setTabCompleter(new CommandTabCompleter(this.plugin));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("serverthings.listall")) {
			if(args.length == 0) {
				sender.sendMessage("§cPlease choose an entity type to list");
				return true;
			}
			else {
				int listed = 0;
				for (World world : Bukkit.getServer().getWorlds()) {
					EntityType entityQuery = MinecraftUtils.entityTypeFromString(args[0]);
					if(entityQuery == null) {
						entityQuery = MinecraftUtils.entityTypeFromString("minecraft:" + args[0]);
					}
					for(Entity entity : world.getEntities()) {
						if(entityQuery == null) {
							if(args[0].equals("minecraft:monster") || args[0].equals("monster")) {
								if(entity instanceof Monster) {
									listed++;
								}
							}
						}
						else {
							if(entity.getType().equals(entityQuery)) {
								listed++;
							}
						}
						
					}
				}

				if(listed == 0) {
					sender.sendMessage("No entities found for " + args[0]);
				}
				else if(listed == 1) {
					sender.sendMessage("1 of " + args[0] + " found");
				}
				else {
					sender.sendMessage(Integer.toString(listed) + " of " + args[0] + " found");
				}
				return true;
			}
		}
		else {
			sender.sendMessage("§cYou do not have permission");
			return true;
		}
	}
}
