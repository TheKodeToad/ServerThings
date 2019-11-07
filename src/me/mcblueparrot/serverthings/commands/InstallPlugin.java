package me.mcblueparrot.serverthings.commands;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import me.mcblueparrot.serverthings.Main;
import me.mcblueparrot.serverthings.events.CommandTabCompleter;

public class InstallPlugin implements CommandExecutor{
	
	private Main plugin;
	
	public InstallPlugin(Main plugin) {
		this.plugin = plugin;
		this.plugin.getCommand("installplugin").setExecutor(this);
		this.plugin.getCommand("installplugin").setTabCompleter(new CommandTabCompleter(this.plugin));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("serverthings.installplugin")) {
			if(args.length > 0) {
				InputStream in;
				try {
					in = new URL(args[0]).openStream();
				} catch (Exception e1) {
					sender.sendMessage("§cPlease use a valid url");
					return true;
				}
				String[] splitargs = args[0].split("\\/");
				String filename = splitargs[splitargs.length - 1];
				try {
					sender.sendMessage("Installing plugin to " + filename + "...");
					Files.copy(in, Paths.get(new File(plugin.getDataFolder().getParentFile().getAbsolutePath(), filename).getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
					sender.sendMessage("Installed plugin");
					
				} catch (IOException e) {
					sender.sendMessage("Error installing file");
				}
				return true;
			}
			else {
				sender.sendMessage("§cPlease specify a web adress for a server plugin");
			}
			return true;
		}
		else {
			sender.sendMessage("§cYou do no have permission");
			return true;
		}
	}
	
}
