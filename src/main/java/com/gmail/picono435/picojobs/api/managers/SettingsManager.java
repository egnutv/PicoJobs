package com.gmail.picono435.picojobs.api.managers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.gmail.picono435.picojobs.PicoJobsPlugin;

public class SettingsManager {
	
	private PicoJobsPlugin plugin;
	
	private String prefix;
	private String lang;
	private String storageMethod;
	private int commandAction;
	private Map<String, Integer> allowedCommands = new HashMap<String, Integer>();
	private ConfigurationSection remoteSqlConfiguration;
	private ConfigurationSection mongodbConfiguration;
	private int salaryCooldown;
	private boolean autoWorking;
	
	public SettingsManager(PicoJobsPlugin plugin) {
		this.plugin = plugin;
		
		reloadConfigurations();
	}
	
	/**
	 * Reload all the configurations
	 * 
	 * @return true if no errors, false if there is errors
	 */
	public boolean reloadConfigurations() {
		FileConfiguration config = this.plugin.getConfig();
		this.prefix = config.getString("prefix");
		this.lang = config.getString("lang");
		this.storageMethod = config.getConfigurationSection("storage").getString("storage-method");
		this.commandAction = config.getInt("jobs-action");
		for(String cmd : config.getConfigurationSection("commands").getKeys(false)) {
			allowedCommands.put(cmd, config.getConfigurationSection("commands").getInt(cmd));
		}
		this.remoteSqlConfiguration = config.getConfigurationSection("storage").getConfigurationSection("remote-sql");
		this.mongodbConfiguration = config.getConfigurationSection("storage").getConfigurationSection("mongodb");
		this.salaryCooldown = config.getInt("salary-cooldown");
		this.autoWorking = config.getBoolean("auto-working");
		return true;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public String getLanguage() {
		return lang;
	}
	
	public String getStorageMethod() {
		return storageMethod;
	}
	
	public int getCommandAction() {
		return commandAction;
	}
	
	public Map<String, Integer> getAllowedCommands() {		
		return allowedCommands;
	}
	
	public ConfigurationSection getRemoteSqlConfiguration() {
		return remoteSqlConfiguration;
	}
	
	public ConfigurationSection getMongoDBConfiguration() {
		return mongodbConfiguration;
	}
	
	public int getSalaryCooldown() {
		return salaryCooldown;
	}

	public boolean isAutoWorking() {
		return autoWorking;
	}
}
