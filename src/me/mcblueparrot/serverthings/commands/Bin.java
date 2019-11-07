package me.mcblueparrot.serverthings.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import me.mcblueparrot.serverthings.Main;
import me.mcblueparrot.serverthings.events.CommandTabCompleter;

public class Bin implements CommandExecutor {
	
	private Main plugin;
	
	public Bin(Main plugin) {
		this.plugin = plugin;
		this.plugin.getCommand("bin").setExecutor(this);
		this.plugin.getCommand("bin").setTabCompleter(new CommandTabCompleter(this.plugin));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("serverthings.enchanting")) {
			if(sender instanceof Player) {
				Player target = (Player) sender;
				Inventory bin = Bukkit.createInventory(null, InventoryType.DROPPER, "Bin");
				target.openInventory(bin);
				return true;
			}
			else {
				sender.sendMessage("§cYou are not a player");
				return true;
			}
			
		}
		else {
			sender.sendMessage("§cYou do no have permission");
			return true;
		}
	}

}
