package com.sbezboro.standardplugin.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import com.sbezboro.standardplugin.VanillaPlugin;

public class PlayerBucketEmptyListener extends EventListener implements Listener {
	public PlayerBucketEmptyListener(VanillaPlugin plugin) {
		super(plugin);
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
		Block block = event.getBlockClicked().getRelative(event.getBlockFace());

		if (block.getType() == Material.END_PORTAL) {
			// Prevent glitch-destroying end portals
			event.setCancelled(true);
		}
	}
}
