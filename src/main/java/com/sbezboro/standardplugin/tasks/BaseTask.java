package com.sbezboro.standardplugin.tasks;

import org.bukkit.scheduler.BukkitRunnable;

import com.sbezboro.standardplugin.VanillaPlugin;

public abstract class BaseTask extends BukkitRunnable {
	protected VanillaPlugin plugin;
	
	public BaseTask(VanillaPlugin plugin) {
		this.plugin = plugin;
	}
}
