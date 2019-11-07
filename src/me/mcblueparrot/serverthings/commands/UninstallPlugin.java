package me.mcblueparrot.serverthings.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import me.mcblueparrot.serverthings.Main;
import me.mcblueparrot.serverthings.events.CommandTabCompleter;

public class UninstallPlugin implements CommandExecutor{
	
	private Main plugin;
	
	public UninstallPlugin(Main plugin) {
		this.plugin = plugin;
		this.plugin.getCommand("uninstallplugin").setExecutor(this);
		this.plugin.getCommand("uninstallplugin").setTabCompleter(new CommandTabCompleter(this.plugin));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("serverthings.uninstallplugin")) {
			if(args.length > 0) {
				String filename = args[0];
				//PluginManager pluginman = Bukkit.getPluginManager();
				if(Files.exists(Paths.get(new File(plugin.getDataFolder().getParentFile().getAbsolutePath(), filename).getAbsolutePath()), LinkOption.NOFOLLOW_LINKS)) {
					try {
						sender.sendMessage("Deleting " + args[0] + "...");
						Files.delete(Paths.get(new File(plugin.getDataFolder().getParentFile().getAbsolutePath(), filename).getAbsolutePath()));
						sender.sendMessage("Deleted " + args[0]);
					} catch (IOException e) {
						sender.sendMessage("§cThere was an error deleting the plugin file");
					}
					return true;
				}
				sender.sendMessage("§cCannot find file " + args[0]);
				return true;
			}
			else {
				sender.sendMessage("§cPlease specify a plugin file to delete");
			}
			return true;
		}
		else {
			sender.sendMessage("§cYou do no have permission");
			return true;
		}
	}
	
}
