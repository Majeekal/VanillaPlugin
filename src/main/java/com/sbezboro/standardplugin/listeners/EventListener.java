package com.sbezboro.standardplugin.listeners;

import com.sbezboro.standardplugin.VanillaPlugin;

public abstract class EventListener {
	protected VanillaPlugin plugin;

	public EventListener(VanillaPlugin plugin) {
		this.plugin = plugin;
	}
}
