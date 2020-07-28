package com.sbezboro.standardplugin.events;

import com.sbezboro.standardplugin.model.StandardPlayer;
import org.bukkit.entity.LivingEntity;

public class KillEvent {
	private StandardPlayer killer;
	private LivingEntity victim;

	public KillEvent(StandardPlayer killer, LivingEntity victim) {
		this.killer = killer;
		this.victim = victim;
	}
}
