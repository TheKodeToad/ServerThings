package me.mcblueparrot.serverthings.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import me.mcblueparrot.serverthings.Main;
import me.mcblueparrot.serverthings.events.CommandTabCompleter;
import me.mcblueparrot.serverthings.utils.MinecraftUtils;

public class KillAll implements CommandExecutor {
	
	private Main plugin;
	
	public KillAll(Main plugin) {
		this.plugin = plugin;
		this.plugin.getCommand("killall").setExecutor(this);
		this.plugin.getCommand("killall").setTabCompleter(new CommandTabCompleter(this.plugin));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("serverthings.killall")) {
			if(args.length == 0) {
				sender.sendMessage("§cPlease choose an entity type to kill");
				return true;
			}
			else {
				int killed = 0;
				for (World world : Bukkit.getServer().getWorlds()) {
					EntityType entityQuery = MinecraftUtils.entityTypeFromString(args[0]);
					if(entityQuery == null) {
						entityQuery = MinecraftUtils.entityTypeFromString("minecraft:" + args[0]);
					}
					for(Entity entity : world.getEntities()) {
						if(entityQuery == null) {
							if(args[0].equals("minecraft:monster") || args[0].equals("monster")) {
								if(entity instanceof Monster) {
									((LivingEntity) entity).damage(Long.MAX_VALUE);
									killed++;
								}
							}
						}
						else {
							if(entity.getType().equals(entityQuery)) {
								if(entity instanceof LivingEntity) {
									LivingEntity livingEntity = (LivingEntity) entity;
									if(entity instanceof Player) {
										Player target = (Player) entity;
										target.damage(Long.MAX_VALUE);
									}
									else if(entity instanceof EnderDragon) {
										((LivingEntity) entity).setHealth(0);
									}
									else {
										livingEntity.damage(Long.MAX_VALUE);
									}
								}
								else {
									entity.remove();
								}
								killed++;
							}
						}
						
					}
				}

				if(killed == 0) {
					sender.sendMessage("No entities were killed");
				}
				else if(killed == 1) {
					sender.sendMessage("1 entity was killed");
				}
				else {
					sender.sendMessage(Integer.toString(killed) + " entities were killed");
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
