package me.mcblueparrot.serverthings.commands;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import me.mcblueparrot.serverthings.Main;
import me.mcblueparrot.serverthings.events.CommandTabCompleter;
import me.mcblueparrot.serverthings.utils.Utils;

public class EnablePlugin implements CommandExecutor{
	
	private Main plugin;
	
	public EnablePlugin(Main plugin) {
		this.plugin = plugin;
		this.plugin.getCommand("enableplugin").setExecutor(this);
		this.plugin.getCommand("enableplugin").setTabCompleter(new CommandTabCompleter(this.plugin));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("serverthings.enableplugin")) {
			if(args.length > 0) {
				ArrayList<String> pluginnames = new ArrayList<String>();
				PluginManager pluginman = Bukkit.getPluginManager();
				for(Plugin plugin : pluginman.getPlugins()) {
					pluginnames.add(plugin.getName());
				}
				if(!pluginnames.contains(args[0])) {
					sender.sendMessage("§cCannot find a plugin called " + args[0]);
					return true;
				}
				if(args[0].toLowerCase().equals("serverthings")) {
					sender.sendMessage("§cI cannot enable myself");
					return true;
				}
				if(pluginman.getPlugin(args[0]).isEnabled()) {
					sender.sendMessage("§c" + args[0] + " is already enabled");
					return true;
				}
				plugin.reloadConfig();
				if(plugin.getConfig().getString("disabledplugins") == null) {
					plugin.getConfig().set("disabledplugins", "");
				}
				ArrayList<String> removedplugins = new ArrayList<String>(Arrays.asList(plugin.getConfig().getString("disabledplugins").split(",")));
				if(removedplugins.contains(args[0])) {
					removedplugins.remove(args[0]);
				}
				plugin.getConfig().set("disabledplugins", Utils.joinArrayListWith(removedplugins, ","));
				plugin.saveConfig();
				pluginman.enablePlugin(pluginman.getPlugin(args[0]));
				sender.sendMessage("Enabled " + args[0]);
				return true;
			}
			else {
				sender.sendMessage("§cPlease choose a plugin to enable");
			}
			return true;
		}
		else {
			sender.sendMessage("§cYou do no have permission");
			return true;
		}
	}
	
}
