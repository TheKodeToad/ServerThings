package me.mcblueparrot.serverthings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.mcblueparrot.serverthings.commands.*;
import me.mcblueparrot.serverthings.events.*;

public class Main extends JavaPlugin {
	
	public God godcommand;
	public AdminGui adminguicommand;
	public Mute mutecommand;
	
	@Override
	public void onEnable() {
		Logger logger = getLogger();
		PluginManager pluginman = Bukkit.getServer().getPluginManager();
		//Config
		saveDefaultConfig();
		//Commands
		new Fly(this);// /fly
		new MakeWarp(this);// /makewarp
		new Warp(this);// /warp
		new ListWarps(this);// /listwarps
		new DelWarp(this);// /delwarp
		new NameItem(this);// /nameitem
		new ReloadConfig(this);// /reloadconfig
		new Do(this);// /do
		new KillAll(this);// /killall
		new Workbench(this);// /workbench
		new Bin(this);// /enchanting
		new ListAll(this);
		new ResourcePack(this);
		new Difficulty(this);
		new Pvp(this);
		new ChatFlilter(this);
		new DisablePlugin(this);
		new EnablePlugin(this);
		new InstallPlugin(this);
		new UninstallPlugin(this);
		adminguicommand = new AdminGui(this);
		godcommand = new God(this);
		mutecommand = new Mute(this);
		//Events
		pluginman.registerEvents(new PlayerEvents(this), this);
		pluginman.registerEvents(new Chat(this), this);
		logger.info("ServerThings loaded");
		if(getConfig().getString("disabledplugins") != null) {
			ArrayList<String> pluginnames = new ArrayList<String>();
			for(Plugin plugin : pluginman.getPlugins()) {
				pluginnames.add(plugin.getName());
			}
			ArrayList<String> plugins = new ArrayList<String>(Arrays.asList(getConfig().getString("disabledplugins").split(",")));
			for(String pluginToDisable : plugins) {
				if(pluginnames.contains(pluginToDisable)) {
					pluginman.disablePlugin(pluginman.getPlugin(pluginToDisable));
				}
			}
			
		}
	}
	
}