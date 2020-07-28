package com.sbezboro.standardplugin.tasks;

import com.sbezboro.standardplugin.VanillaPlugin;
import com.sbezboro.standardplugin.model.StandardPlayer;

public class PvpTimerTask extends BaseTask {
	private StandardPlayer player;

	public PvpTimerTask(VanillaPlugin plugin, StandardPlayer player) {
		super(plugin);
		
		this.player = player;
	}

	@Override
	public void run() {
		if (player.isOnline()) {
			player.setNotInPvp();
		}
	}

}
