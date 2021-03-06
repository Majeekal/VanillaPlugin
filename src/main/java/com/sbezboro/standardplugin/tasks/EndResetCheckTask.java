package com.sbezboro.standardplugin.tasks;

import com.sbezboro.standardplugin.VanillaPlugin;

public class EndResetCheckTask extends BaseTask {
	public EndResetCheckTask(VanillaPlugin plugin) {
		super(plugin);
	}

	@Override
	public void run() {
		if (!plugin.getEndResetManager().isEndResetScheduled()) {
			return;
		}
		
		long curTime = System.currentTimeMillis();
		
		long nextEndReset = plugin.getEndResetStorage().getNextReset();
		
		int totalSeconds = (int) ((nextEndReset - curTime) / 1000);
		
		if (totalSeconds <= 0) {
			plugin.getEndResetManager().resetEnd();
		}
	}

}
