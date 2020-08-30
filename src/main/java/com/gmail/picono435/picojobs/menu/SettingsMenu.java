package com.gmail.picono435.picojobs.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.gmail.picono435.picojobs.PicoJobsPlugin;
import com.gmail.picono435.picojobs.api.Job;
import com.gmail.picono435.picojobs.api.PicoJobsAPI;
import com.gmail.picono435.picojobs.utils.ItemBuilder;

import net.md_5.bungee.api.ChatColor;

public class SettingsMenu {
	
	// 27 - PEQUENO
	// 54 - GRANDE
	
	public static List<Inventory> generalInventories = new ArrayList<Inventory>();
	public static List<Inventory> jobListInventories = new ArrayList<Inventory>();
	public static Map<Inventory, Job> jobSettingsInventories = new HashMap<Inventory, Job>();
	public static Map<Inventory, Job> jobEditInventories = new HashMap<Inventory, Job>();
	
	public static void openGeneral(Player p) {
		Inventory inv = Bukkit.createInventory(null, 27, "PicoJobs - Settings");
		
		inv.setItem(13, new ItemBuilder(Material.CHEST).setName(ChatColor.AQUA + "Job Settings").removeAttributes().toItemStack());
	
		p.openInventory(inv);
		generalInventories.add(inv);
	}
	
	public static void openJobsList(Player p) {
		Inventory inv = Bukkit.createInventory(null, 54, "PicoJobs - Settings");
		
		List<Job> jobs = new ArrayList<Job>(PicoJobsAPI.getJobsManager().getJobs());
		for(int i = 0; i < jobs.size(); i++) {
			inv.setItem(i, jobs.get(i).getFormattedItem());
		}
	
		p.openInventory(inv);
		jobListInventories.add(inv);
	}
	
	public static void openJobSettings(Player p, Job job) {
		Inventory inv = Bukkit.createInventory(null, 27, "PicoJobs - Settings");
		
		inv.setItem(11, new ItemBuilder(Material.PAPER).setName(job.getDisplayName()).setLore("", ChatColor.GRAY + "These are the basic settings of the",  ChatColor.GRAY + " job " + job.getID(), "").removeAttributes().toItemStack());
		inv.setItem(13, new ItemBuilder(Material.TRIPWIRE_HOOK).setName(ChatColor.GOLD + "Edit Job").setLore("", ChatColor.GRAY + "Click here to open the advanced",  ChatColor.GRAY + " settings of the job.", "").removeAttributes().toItemStack());
		inv.setItem(15, new ItemBuilder(Material.BARRIER).setName(ChatColor.RED + "Delete Job").setLore("", ChatColor.GRAY + "Click here to delete the job.", "",  ChatColor.RED + "THIS ACTION CANNOT BE UNDONE", "").removeAttributes().toItemStack());
		
		p.openInventory(inv);
		jobSettingsInventories.put(inv, job);
	}
	
	public static void openJobEdit(Player p, Job job) {
		Inventory inv = Bukkit.createInventory(null, 54, "PicoJobs - Settings");
		
		inv.setItem(10, new ItemBuilder(Material.NAME_TAG).setName(ChatColor.AQUA + "ID").setLore("", ChatColor.GRAY + "ID cannot be changed.", "", ChatColor.DARK_GRAY + "Current ID: " + job.getID(), "").removeAttributes().toItemStack());
		if(PicoJobsPlugin.getInstance().isLegacy()) {
			inv.setItem(11, new ItemBuilder(Enum.valueOf(Material.class, "BOOK_AND_QUILL")).setName(ChatColor.AQUA + "Display Name").setLore("", ChatColor.GRAY + "Click here to change the Display Name.", "", ChatColor.DARK_GRAY + "Current displayname: " + job.getDisplayName(), "").removeAttributes().toItemStack());
		} else {
			inv.setItem(11, new ItemBuilder(Material.WRITABLE_BOOK).setName(ChatColor.AQUA + "Display Name").setLore("", ChatColor.GRAY + "Click here to change the Display Name.", "", ChatColor.DARK_GRAY + "Current displayname: " + job.getDisplayName(), "").removeAttributes().toItemStack());
		}
		inv.setItem(15, new ItemBuilder(Material.PAPER).setName(ChatColor.AQUA + "Salary").setLore("", ChatColor.GRAY + "Click here to change the salary.", "", ChatColor.DARK_GRAY + "Current salary: " + job.getSalary(), "").removeAttributes().toItemStack());
		inv.setItem(19, new ItemBuilder(Material.PISTON).setName(ChatColor.AQUA + "Job Type").setLore("", ChatColor.GRAY + "Click here to change the job type.", "", ChatColor.DARK_GRAY + "Current type: " + job.getType().name(), "").removeAttributes().toItemStack());
		inv.setItem(20, new ItemBuilder(Material.EMERALD_BLOCK).setName(ChatColor.AQUA + "Economy Implementation").setLore("", ChatColor.GRAY + "Click here to change the Economy Implementation.", "", ChatColor.DARK_GRAY + "Current economy: " + job.getEconomy(), "").removeAttributes().toItemStack());
		inv.setItem(24, new ItemBuilder(Material.GLASS).setName(ChatColor.AQUA + "Required Method").setLore("", ChatColor.GRAY + "Click here to change the required method.", "", ChatColor.DARK_GRAY + "Current required method: " + job.getMethod(), "").removeAttributes().toItemStack());
		inv.setItem(33, new ItemBuilder(Material.TORCH).setName(ChatColor.AQUA + "Salary Frequency").setLore("", ChatColor.GRAY + "Click here to change the Salary Frequency.", "", ChatColor.DARK_GRAY + "Current salary frequency: " + job.getSalaryFrequency(), "").removeAttributes().toItemStack());
		if(job.getWhitelistArray() != null) {
			ItemBuilder whitelistBuilder = new ItemBuilder(Material.BOOKSHELF).setName(ChatColor.AQUA + "Whitelist/Blacklist").setLore("", ChatColor.GRAY + "Click here to change the whitelist.", "", ChatColor.DARK_GRAY + "Current whitelist: ");
			int i = 0;
			String finalString = "";
			for(String s : job.getWhitelistArray().split(" ")) {
				if(i >= 3) {
					whitelistBuilder.addLoreLine(ChatColor.DARK_GRAY + finalString);
					i = 0;
					finalString = "";
				}
				finalString = finalString + " " + s;
				i++;
			}
			if(!finalString.equals("")) {
				whitelistBuilder.addLoreLine(ChatColor.DARK_GRAY + finalString);
			}
			whitelistBuilder.addLoreLine("");
			whitelistBuilder.removeAttributes();
			inv.setItem(37, whitelistBuilder.toItemStack());
			String white = "Whitelist";
			if(!job.isWhitelist()) {
				white = "Blacklist";
			}
			inv.setItem(38, new ItemBuilder(Material.BOOK).setName(ChatColor.AQUA + "Whitelist Type").setLore("", ChatColor.GRAY + "Click here to change from Whitelist type", ChatColor.GRAY + " to Blacklist type or vs.", "", ChatColor.DARK_GRAY + "Current whitelist type: " + white, "").removeAttributes().toItemStack());
			if(job.isWhitelist()) {
				inv.setItem(38, new ItemBuilder(Material.BOOK).setName(ChatColor.AQUA + "Whitelist Type").setLore("", ChatColor.GRAY + "Click here to change from Whitelist type", ChatColor.GRAY + " to Blacklist type or vs.", "", ChatColor.DARK_GRAY + "Current whitelist type: " + white, "").addEnchant(Enchantment.ARROW_DAMAGE, 1).removeAttributes().toItemStack());
			}
		}
		inv.setItem(39, new ItemBuilder(Material.CHEST).setName(ChatColor.AQUA + "GUI Settings").setLore("", ChatColor.GRAY + "Click here to access the GUI settings.", "").removeAttributes().toItemStack());
		inv.setItem(40, new ItemBuilder(Material.OAK_FENCE).setName(ChatColor.AQUA + "Requires Permission").setLore("", ChatColor.GRAY + "Click here to change the Requires Permission.", "", ChatColor.DARK_GRAY + "Current requires permission: " + job.getMethodFrequency(), ChatColor.DARK_GRAY + "picojobs.job." + job.getID(), "").removeAttributes().toItemStack());
		if(job.requiresPermission()) {
			inv.setItem(40, new ItemBuilder(Material.OAK_FENCE).setName(ChatColor.AQUA + "Requires Permission").setLore("", ChatColor.GRAY + "Click here to change the Requires Permission.", "", ChatColor.DARK_GRAY + "Current requires permission: " + job.getMethodFrequency(), ChatColor.DARK_GRAY + "picojobs.job." + job.getID(), "").addEnchant(Enchantment.ARROW_DAMAGE, 1).removeAttributes().toItemStack());
		}
		inv.setItem(42, new ItemBuilder(Material.TORCH).setName(ChatColor.AQUA + "Method Frequency").setLore("", ChatColor.GRAY + "Click here to change the Method Frequency.", "", ChatColor.DARK_GRAY + "Current method frequency: " + job.getMethodFrequency(), "").removeAttributes().toItemStack());
		
		p.closeInventory();
		p.openInventory(inv);
		jobEditInventories.put(inv, job);
	}
}
