package me.mcblueparrot.serverthings.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.mcblueparrot.serverthings.Main;

public class PlayerEvents implements Listener{
	
	private Main plugin;
	
	public PlayerEvents(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
		plugin.reloadConfig();
		Player target = e.getPlayer();
		Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
			public void run() {
				target.setResourcePack(plugin.getConfig().getString("resourcepack"));
			}
		}, 1L);
        
        if(plugin.getConfig().getString("saveflight").equals("true")) {
        	if(plugin.getConfig().getString(("flight." + target.getName())).equals("true")) {
            	target.setAllowFlight(true);
        	}
        	else if(plugin.getConfig().getString(("flight." + target.getName())).equals("false")){
        		target.setAllowFlight(false);
        	}
        }
        if(plugin.getConfig().getString(("god." + target.getName())).equals("true")) {
        	plugin.godcommand.gods.add(target.getName());
        }
        
        String commandtorun = plugin.getConfig().getString("joincmd");
        commandtorun = commandtorun.replaceAll("%p", target.getName());
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), commandtorun);
    }
	

	@EventHandler
    public void onPlayerQuit(PlayerQuitEvent e)
    {
        Player target = e.getPlayer();
        boolean canFly = target.getAllowFlight();
        if(plugin.getConfig().getString("saveflight") == "true") {
			plugin.getConfig().set("flight." + target.getName(), Boolean.toString(canFly));
			plugin.saveConfig();
		}
    }
	
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player target = (Player) e.getEntity();
			if(plugin.godcommand.gods.contains(target.getName())) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void inventoryClick(InventoryClickEvent e) {
		Player target = (Player) e.getWhoClicked();
		if(e.getView().getTitle().equals("Admin Gui")){
			e.setCancelled(true);
			if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§rDay§r")) {
				Bukkit.getServer().dispatchCommand(target, "time set day");
			}
			else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§rNoon§r")) {
				Bukkit.getServer().dispatchCommand(target, "time set noon");
			}
			else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§rNight§r")) {
				Bukkit.getServer().dispatchCommand(target, "time set night");
			}
			else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§rMidnight§r")) {
				Bukkit.getServer().dispatchCommand(target, "time set midnight");
			}
			else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§rSunny§r")) {
				Bukkit.getServer().dispatchCommand(target, "weather clear");
			}
			else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§rRainy§r")) {
				Bukkit.getServer().dispatchCommand(target, "weather rain");
			}
			else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§rThunder Storm§r")) {
				Bukkit.getServer().dispatchCommand(target, "weather thunder");
			}
		}
	}
	
	@EventHandler
	public void onPlayerUse(PlayerInteractEvent e) {
		Player target = e.getPlayer();
		if(!(target.getInventory().getItemInMainHand().getType() == Material.AIR)) {
			boolean itemInConfig = plugin.getConfig().contains("itemcmds." + target.getInventory().getItemInMainHand().getItemMeta().getDisplayName());
			if(itemInConfig) {
				String commandtorun = plugin.getConfig().getString("itemcmds." + target.getInventory().getItemInMainHand().getItemMeta().getDisplayName());
				commandtorun = commandtorun.replaceAll("%p", target.getName());
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), commandtorun);
			}
		}
	}
}
