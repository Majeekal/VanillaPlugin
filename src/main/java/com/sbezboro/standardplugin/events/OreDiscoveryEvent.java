package com.sbezboro.standardplugin.events;

import org.bukkit.Location;

import com.sbezboro.http.HttpRequestManager;
import com.sbezboro.standardplugin.model.StandardPlayer;

public class OreDiscoveryEvent {
	private StandardPlayer player;
	private String type;
	private Location location;
	
	public OreDiscoveryEvent(StandardPlayer player, String type, Location location) {
		this.player = player;
		this.type = type;
		this.location = location;
	}
}
