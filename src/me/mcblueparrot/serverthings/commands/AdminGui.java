package me.mcblueparrot.serverthings.commands;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

import me.mcblueparrot.serverthings.Main;
import me.mcblueparrot.serverthings.events.CommandTabCompleter;
import me.mcblueparrot.serverthings.utils.MinecraftUtils;

public class AdminGui implements CommandExecutor{
	
	private Main plugin;
	public Inventory gui;
	
	public AdminGui(Main plugin) {
		this.plugin = plugin;
		this.plugin.getCommand("admingui").setExecutor(this);
		this.plugin.getCommand("admingui").setTabCompleter(new CommandTabCompleter(this.plugin));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("serverthings.admingui")) {
			if(sender instanceof Player) {
				Player target = (Player) sender;
				gui = Bukkit.createInventory(null, 54, "Admin Gui");
				gui.setItem(0, MinecraftUtils.itemstack(new ItemStack(Material.CLOCK), "§rTime§r"));
				gui.setItem(6, MinecraftUtils.itemstack(new ItemStack(Material.WATER_BUCKET), "§rWeather§r"));
				gui.setItem(9, MinecraftUtils.itemstack(new ItemStack(Material.LIGHT_BLUE_CONCRETE), "§rDay§r"));
				gui.setItem(10, MinecraftUtils.itemstack(new ItemStack(Material.BLUE_CONCRETE), "§rNoon§r"));
				gui.setItem(11, MinecraftUtils.itemstack(new ItemStack(Material.PURPLE_CONCRETE), "§rNight§r"));
				gui.setItem(12, MinecraftUtils.itemstack(new ItemStack(Material.BLACK_CONCRETE), "§rMidnight§r"));
				gui.setItem(15, MinecraftUtils.itemstack(new ItemStack(Material.SUNFLOWER), "§rSunny§r"));
				ItemStack waterbottle = MinecraftUtils.itemstack(new ItemStack(Material.SPLASH_POTION), "§rRainy§r");
				PotionMeta watermeta = (PotionMeta) waterbottle.getItemMeta();
				watermeta.setColor(Color.BLUE);
				waterbottle.setItemMeta(watermeta);
				gui.setItem(16, waterbottle);
				gui.setItem(17, MinecraftUtils.itemstack(new ItemStack(Material.FIRE_CHARGE), "§rThunder Storm§r"));
				gui.setItem(27, MinecraftUtils.itemstack(new ItemStack(Material.DIAMOND_SWORD), "§rDifficulty§r"));
				gui.setItem(32, MinecraftUtils.itemstack(new ItemStack(Material.LEVER), "§rGame Mode§r"));
				gui.setItem(36, MinecraftUtils.itemstack(new ItemStack(Material.POPPY), "§rPeaceful§r"));
				gui.setItem(37, MinecraftUtils.itemstack(new ItemStack(Material.WOODEN_SWORD), "§rEasy§r"));
				gui.setItem(38, MinecraftUtils.itemstack(new ItemStack(Material.ZOMBIE_HEAD), "§rNormal§r"));
				gui.setItem(39, MinecraftUtils.itemstack(new ItemStack(Material.WITHER_SKELETON_SKULL), "§rHard§r"));
				gui.setItem(41, MinecraftUtils.itemstack(new ItemStack(Material.COOKED_BEEF), "§rSurvival§r"));
				gui.setItem(42, MinecraftUtils.itemstack(new ItemStack(Material.BRICKS), "§rCreative§r"));
				gui.setItem(43, MinecraftUtils.itemstack(new ItemStack(Material.GOLDEN_SWORD), "§rAdventure§r"));
				gui.setItem(44, MinecraftUtils.itemstack(new ItemStack(Material.ENDER_EYE), "§rSpectator§r"));
				gui.setItem(45, MinecraftUtils.itemstack(new ItemStack(Material.LEATHER_BOOTS), "§rKick Player§r"));
				gui.setItem(49, MinecraftUtils.itemstack(new ItemStack(Material.ARROW), "§rClose§r"));
				gui.setItem(53, MinecraftUtils.itemstack(new ItemStack(Material.BARRIER), "§rBan Player§r"));
				target.openInventory(gui);
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
