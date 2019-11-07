package me.mcblueparrot.serverthings.utils;

import java.util.ArrayList;

import org.bukkit.ChatColor;

public class Utils {
	
	public static String chatColours(String text) {
		return ChatColor.translateAlternateColorCodes('^', text);
	}
	
	public static String joinArgs(String[] args) {
		String result = "";
		int times = 1;
		for(String arg : args) {
			if(times == args.length) {
				result += arg;
			}
			else {
				result += arg + " ";
			}
			times++;
		}
		return result;
	}
	
	public static String joinArgsWith(String[] args, String cement) {
		String result = "";
		int times = 1;
		for(String arg : args) {
			if(times == args.length) {
				result += arg;
			}
			else {
				result += arg + cement;
			}
			times++;
		}
		return result;
	}
	public static String joinArrayListWith(ArrayList<String> args, String cement) {
		String result = "";
		int times = 1;
		for(String arg : args) {
			if(times == args.size()) {
				result += arg;
			}
			else {
				result += arg + cement;
			}
			times++;
		}
		return result;
	}
	
	public static ArrayList<String> arrayListFilter(ArrayList<String> array, String filter){
		ArrayList<String> newArray = new ArrayList<String>();
		for(String str : array) {
			if(str.toLowerCase().contains(filter.toLowerCase())) {
				newArray.add(str);
			}
		}
		return newArray;
	}
	
	public static ArrayList<String> arrayListCommandFilter(ArrayList<String> array, String filter){
		ArrayList<String> newArray = new ArrayList<String>();
		for(String str : array) {
			if(str.toLowerCase().startsWith(filter.toLowerCase())) {
				newArray.add(str);
			}
		}
		return newArray;
	}
	
	public static ArrayList<String> arrayListCommandIdFilter(ArrayList<String> array, String filter){
		ArrayList<String> newArray = new ArrayList<String>();
		for(String str : array) {
			if(str.startsWith(filter.toLowerCase())) {
				newArray.add(str);
			}
			else if(str.startsWith("minecraft:")) {
				if(str.startsWith(filter.toLowerCase(), 10)) {
					newArray.add(str);
				}
			}
		}
		return newArray;
	}
	
	public static String capitalizeFirstLetter(String original) {
	    if (original == null || original.length() == 0) {
	        return original;
	    }
	    return original.substring(0, 1).toUpperCase() + original.substring(1);
	}
	
}