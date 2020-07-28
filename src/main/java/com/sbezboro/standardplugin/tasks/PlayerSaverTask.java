package com.sbezboro.standardplugin.tasks;

import com.sbezboro.standardplugin.VanillaPlugin;
import com.sbezboro.standardplugin.model.StandardPlayer;

public class PlayerSaverTask extends BaseTask {

	public PlayerSaverTask(VanillaPlugin plugin) {
		super(plugin);
	}

	@Override
	public void run() {
		for (StandardPlayer player : VanillaPlugin.getPlugin().getOnlinePlayers()) {
			player.saveData();
		}
	}

}
