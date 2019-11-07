package me.mcblueparrot.serverthings.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.mcblueparrot.serverthings.Main;

public class Chat implements Listener{
	
	private Main plugin;
	
	public Chat(Main plugin) {
		this.plugin = plugin;
	}

	
	@EventHandler
	public void playerChat(AsyncPlayerChatEvent e){
		if(plugin.mutecommand.mutes.contains(e.getPlayer().getName())) {
			e.getPlayer().sendMessage("§cYou have been muted");
			e.setCancelled(true);
		}
		else {
			if(!e.getPlayer().hasPermission("serverthings.rudechat") && plugin.getConfig().getString("chat.filter").equals("true")) {
				boolean cancel = false;
				String message = e.getMessage();
				message = message.toLowerCase();
				message = message.replaceAll(" ", "");
				message = message.replaceAll("[$]", "s");
				message = message.replaceAll("£", "e");
				message = message.replaceAll("!", "i");
				message = message.replaceAll("0", "o");
				String rudewords = plugin.getConfig().getString("rudewords");
				String[] rudewordslist = rudewords.split(",");
				for(String word : rudewordslist) {
					StringBuilder sb = new StringBuilder(word);
					sb.reverse();
					String finalrudeword = sb.toString();
					if(message.contains(finalrudeword)) {
						cancel = true;
						e.getPlayer().sendMessage("§cYour message was not sent");
						if(plugin.getConfig().getString("chat.alertrude").equals("true")) {
							if(plugin.getConfig().getString("chat.showrudemessage").equals("true")) {
								Bukkit.getServer().getLogger().info(e.getPlayer().getName() + " sent a message which was blocked: <" + e.getPlayer().getDisplayName() + "> " + e.getMessage());
							}
							else {
								Bukkit.getServer().getLogger().info(e.getPlayer().getName() + " sent a message which was blocked");
							}
							
						}
						if(plugin.getConfig().getString("chat.rudekick").equals("true")){
							Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
								 
								public void run() {
									e.getPlayer().kickPlayer("You sent a message in chat that was filtered out");
								}
							}, 1L);
							
						}
						break;
					}
				}
			    e.setCancelled(cancel);
			}
		}
	}
}
