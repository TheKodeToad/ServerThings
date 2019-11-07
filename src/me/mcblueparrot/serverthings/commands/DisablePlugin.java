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

public class DisablePlugin implements CommandExecutor{
	
	private Main plugin;
	
	public DisablePlugin(Main plugin) {
		this.plugin = plugin;
		this.plugin.getCommand("disableplugin").setExecutor(this);
		this.plugin.getCommand("disableplugin").setTabCompleter(new CommandTabCompleter(this.plugin));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("serverthings.disableplugin")) {
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
				if(args[0].equals("serverthings")) {
					sender.sendMessage("§cI cannot disable myself");
					return true;
				}
				if(!pluginman.getPlugin(args[0]).isEnabled()) {
					sender.sendMessage("§c" + args[0] + " is already disabled");
					return true;
				}
				plugin.reloadConfig();
				if(plugin.getConfig().getString("disabledplugins") == null) {
					plugin.getConfig().set("disabledplugins", "");
				}
				ArrayList<String> removedplugins = new ArrayList<String>(Arrays.asList(plugin.getConfig().getString("disabledplugins").split(",")));
				sender.sendMessage(removedplugins.toString());
				if(!removedplugins.contains(args[0])) {
					removedplugins.add(args[0]);
				}
				sender.sendMessage(removedplugins.toString());
				sender.sendMessage(Utils.joinArrayListWith(removedplugins, ","));
				plugin.getConfig().set("disabledplugins", Utils.joinArrayListWith(removedplugins, ","));
				plugin.saveConfig();
				sender.sendMessage("§cWarning: Some plugins won't work after re-enabling");
				pluginman.disablePlugin(pluginman.getPlugin(args[0]));
				sender.sendMessage("Disabled " + args[0]);
				return true;
			}
			else {
				sender.sendMessage("§cPlease choose a plugin to disable");
			}
			return true;
		}
		else {
			sender.sendMessage("§cYou do no have permission");
			return true;
		}
	}
	
}
