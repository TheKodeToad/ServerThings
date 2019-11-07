package me.mcblueparrot.serverthings.utils;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.mcblueparrot.serverthings.text.EntityIds;

public class MinecraftUtils {
	public static ArrayList<String> getAllEntitiesString(){
		return new ArrayList<String>(Arrays.asList(EntityIds.text.split("\\r?\\n")));
	}
	
	public static EntityType entityTypeFromString(String entity) {
		if(Arrays.asList(EntityIds.text.split("\\r?\\n")).contains(entity)) {
			return EntityIds.types.get(Arrays.asList(EntityIds.text.split("\\r?\\n")).indexOf(entity));
		}
		return null;
	}
	
	public static ItemStack itemstack(ItemStack material, String displayname) {
		ItemStack stack = material;
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(displayname);
		stack.setItemMeta(meta);
		return stack;
	}
	
	
	
}
