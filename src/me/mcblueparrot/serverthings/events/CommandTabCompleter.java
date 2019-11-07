package me.mcblueparrot.serverthings.events;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import me.mcblueparrot.serverthings.Main;
import me.mcblueparrot.serverthings.utils.MinecraftUtils;
import me.mcblueparrot.serverthings.utils.Utils;

public class CommandTabCompleter implements TabCompleter {
	
	private Main plugin;
	private ArrayList<String> allEntities;
	private ArrayList<String> allEntityTypes;
	
	public CommandTabCompleter(Main plugin) {
		this.plugin = plugin;
		this.allEntities = MinecraftUtils.getAllEntitiesString();
		this.allEntityTypes = allEntities;
		allEntityTypes.add("minecraft:monster");
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("fly")) {
			if(args.length == 1) {
				return null;
			}
			else if(args.length == 2) {
				ArrayList<String> cancannot = new ArrayList<String>(List.of("can", "cannot"));
				return Utils.arrayListCommandFilter(cancannot, args[1]);
			}
		}
		if(cmd.getName().equalsIgnoreCase("god")) {
			if(args.length == 1) {
				return null;
			}
			else if(args.length == 2) {
				ArrayList<String> onoff = new ArrayList<String>(List.of("on", "off"));
				return Utils.arrayListCommandFilter(onoff, args[1]);
			}
		}
		else if(cmd.getName().equalsIgnoreCase("warp")) {
			if(args.length == 1) {
				Set<String> warpsset = plugin.getConfig().getConfigurationSection("warps").getKeys(true);
				ArrayList<String> warps = new ArrayList<String>();
				for(Object name : warpsset) {
					warps.add((String) name);
				}
				return Utils.arrayListCommandFilter(warps, args[0]);
			}
			else if(args.length == 2) {
				return null;
			}
		}
		else if(cmd.getName().equalsIgnoreCase("delwarp")) {
			if(args.length == 1) {
				Set<String> warpsset = plugin.getConfig().getConfigurationSection("warps").getKeys(true);
				ArrayList<String> warps = new ArrayList<String>();
				for(Object name : warpsset) {
					warps.add((String) name);
				}
				return Utils.arrayListCommandFilter(warps, args[0]);
			}
		}
		else if(cmd.getName().equalsIgnoreCase("killall") || cmd.getName().equalsIgnoreCase("listall")) {
			if(args.length == 1) {
				return Utils.arrayListCommandIdFilter(allEntityTypes, args[0]);
			}
		}
		else if(cmd.getName().equalsIgnoreCase("makewarp")) {
			if(args.length == 5) {
				ArrayList<String> worldnames = new ArrayList<String>();
				for(World world : Bukkit.getServer().getWorlds()) {
					worldnames.add(world.getName());
				}
				return Utils.arrayListCommandFilter(worldnames, args[4]);
			}
		}
		else if(cmd.getName().equalsIgnoreCase("resourcepack")) {
			if(args.length == 1) {
				ArrayList<String> globalplayer = new ArrayList<String>(List.of("global", "player"));
				return Utils.arrayListCommandFilter(globalplayer, args[0]);
			}
			else if(args.length == 2 && args[0].equalsIgnoreCase("player")) {
				return null;
			}
		}
		else if(cmd.getName().equalsIgnoreCase("difficulty")) {
			if(args.length == 1) {
				ArrayList<String> difficulties = new ArrayList<String>(List.of("peaceful", "easy", "normal", "hard"));
				return Utils.arrayListCommandFilter(difficulties, args[0]);
			}
		}
		else if(cmd.getName().equalsIgnoreCase("chatfilter")) {
			if(args.length == 1) {
				ArrayList<String> options = new ArrayList<String>(List.of("disable", "enable", "settings", "addword", "removeword"));
				return Utils.arrayListCommandFilter(options, args[0]);
			}
			else if(args.length == 2 && args[0].equalsIgnoreCase("settings")) {
				ArrayList<String> settings = new ArrayList<String>(List.of("kickplayers", "alertplayers", "showplayermessage"));
				return Utils.arrayListCommandFilter(settings, args[1]);
			}
			else if(args.length == 3 && args[0].equalsIgnoreCase("settings")) {
				ArrayList<String> truefalse = new ArrayList<String>(List.of("true", "false"));
				return Utils.arrayListCommandFilter(truefalse, args[2]);
			}
			else if(args.length == 2 && args[0].equalsIgnoreCase("removeword")) {
				String rudewords = plugin.getConfig().getString("rudewords");
				String[] rudewordslist = rudewords.split(",");
				ArrayList<String> rudewordsarraylist = new ArrayList<String>(Arrays.asList(rudewordslist));
				return Utils.arrayListCommandFilter(rudewordsarraylist, args[1]);
			}
		}
		else if(cmd.getName().equalsIgnoreCase("pvp")) {
			if(args.length == 1) {
				ArrayList<String> onoff = new ArrayList<String>(List.of("on", "off"));
				return Utils.arrayListCommandFilter(onoff, args[0]);
			}
		}
		else if(cmd.getName().equalsIgnoreCase("disableplugin")) {
			if(args.length == 1) {
				ArrayList<String> pluginnames = new ArrayList<String>();
				PluginManager pluginman = Bukkit.getPluginManager();
				for(Plugin plugin : pluginman.getPlugins()) {
					if(pluginman.getPlugin(plugin.getName()).isEnabled()) {
						pluginnames.add(plugin.getName());
					}
				}
				if(pluginnames.contains("ServerThings")) {
					pluginnames.remove("ServerThings");
				}
				return Utils.arrayListCommandFilter(pluginnames, args[0]);
			}
		}
		else if(cmd.getName().equalsIgnoreCase("enableplugin")) {
			if(args.length == 1) {
				ArrayList<String> pluginnames = new ArrayList<String>();
				PluginManager pluginman = Bukkit.getPluginManager();
				for(Plugin plugin : pluginman.getPlugins()) {
					if(!pluginman.getPlugin(plugin.getName()).isEnabled()) {
						pluginnames.add(plugin.getName());
					}
				}
				if(pluginnames.contains("ServerThings")) {
					pluginnames.remove("ServerThings");
				}
				return Utils.arrayListCommandFilter(pluginnames, args[0]);
			}
		}
		else if(cmd.getName().equalsIgnoreCase("uninstallplugin")) {
			if(args.length == 1) {
				List<File> files = Arrays.asList(new File(plugin.getDataFolder().getParentFile().getAbsolutePath()).listFiles());
				ArrayList<String> fileNames = new ArrayList<String>();
				for(File file : files) {
					if(file.isFile() && !file.isHidden()) {
						fileNames.add(file.getName());
					}
				}
				return Utils.arrayListCommandFilter(fileNames, args[0]);
			}
		}
		else if(cmd.getName().equalsIgnoreCase("mute")) {
			if(args.length == 1) {
				return null;
			}
		}
		return new ArrayList<String>();
	}

}
