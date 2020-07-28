package com.sbezboro.standardplugin;

import org.bukkit.configuration.Configuration;

import java.util.List;

public class StandardConfig {
	private VanillaPlugin plugin;

	private boolean debug = false;

	private List<String> mutedWords;
	private int pvpProtectionTime;
	private int hungerProtectionTime;
	private int newbieStalkerThreshold;

	private boolean endResetEnabled;
	private boolean generateEndPortals;

	private int pvpLogThreshold;

	private boolean nerfEndermenDrops;
	private boolean nerfPigzombieDrops;

	private int animalChunkCap;

	private int spawnKillTimeout;
	private int deathMessageTimeout;

	public StandardConfig(VanillaPlugin plugin) {
		this.plugin = plugin;
	}

	public void reload() {
		Configuration config = plugin.getConfig();

		plugin.getLogger().info("Plugin starting");

		debug = config.getBoolean("debug");
		if (debug) {
			plugin.getLogger().info("Debug mode enabled!");
		}

		pvpProtectionTime = config.getInt("pvp-protection-time");
		spawnKillTimeout = config.getInt("spawn-kill-timeout");
		deathMessageTimeout = config.getInt("death-message-timeout");
		hungerProtectionTime = config.getInt("hunger-protection-time");
		newbieStalkerThreshold = config.getInt("newbie-stalker-threshold");

		endResetEnabled = config.getBoolean("end-reset-enabled");
		generateEndPortals = config.getBoolean("generate-end-portals");

		pvpLogThreshold = config.getInt("pvp-log-threshold");

		nerfEndermenDrops = config.getBoolean("nerf-endermen-drops");
		nerfPigzombieDrops = config.getBoolean("nerf-pigzombie-drops");

		animalChunkCap = config.getInt("animal-chunk-cap");

		mutedWords = config.getStringList("muted-words");
	}

	public int getPvpProtectionTime() {
		return pvpProtectionTime;
	}

	public int getSpawnKillTimeout() {
		return spawnKillTimeout;
	}

	public int getDeathMessageTimeout() {
		return deathMessageTimeout;
	}

	public int getHungerProtectionTime() {
		return hungerProtectionTime;
	}

	public int getNewbieStalkerThreshold() {
		return newbieStalkerThreshold;
	}

	public boolean isEndResetEnabled() {
		return endResetEnabled;
	}

	public boolean shouldGenerateEndPortals() {
		return generateEndPortals;
	}

	public int getPvpLogThreshold() {
		return pvpLogThreshold;
	}

	public boolean getNerfEndermenDrops() {
		return nerfEndermenDrops;
	}

	public boolean getNerfPigzombieDrops() {
		return nerfPigzombieDrops;
	}

	public int getAnimalChunkCap() {
		return animalChunkCap;
	}

	public List<String> getMutedWords() {
		return mutedWords;
	}
}