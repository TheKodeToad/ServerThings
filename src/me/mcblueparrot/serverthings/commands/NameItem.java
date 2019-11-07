package me.mcblueparrot.serverthings.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import me.mcblueparrot.serverthings.Main;
import me.mcblueparrot.serverthings.utils.Utils;

public class NameItem implements CommandExecutor{
	
	private Main plugin;
	
	public NameItem(Main plugin) {
		this.plugin = plugin;
		this.plugin.getCommand("nameitem").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("serverthings.nameitem")) {
			if(args.length > 0) {
				if(sender instanceof Player) {
					Player target = (Player) sender;
					if(target.getInventory().getItemInMainHand().getType() == Material.AIR) {
						sender.sendMessage("§cPlease hold an item first");
						return true;
					}
					ItemMeta meta = target.getInventory().getItemInMainHand().getItemMeta();
					meta.setDisplayName("§r" + Utils.chatColours(Utils.joinArgs(args)) + "§r");
					target.getInventory().getItemInMainHand().setItemMeta(meta);
					sender.sendMessage("Item named");
					return true;
				}
				else {
					sender.sendMessage("§cYou are not a player");
					return true;
				}
			}
			else {
				sender.sendMessage("§cPlease choose a name");
				return true;
			}
		}
		else {
			sender.sendMessage("§cYou do no have permission");
			return true;
		}
	}
	
}
