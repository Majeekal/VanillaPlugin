package com.sbezboro.standardplugin.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.sbezboro.standardplugin.model.StandardPlayer;
import com.sbezboro.standardplugin.util.MiscUtil;

public class DeathEvent {
	public enum DeathType {
		SUICIDE, FALL, FIRE, LAVA, EXPLOSION, CACTUS, VOID, SUFFOCATION, DROWNING, STARVATION, MAGIC, PVP_LOG, OTHER
	}

	private StandardPlayer player;
	private EntityDamageEvent damageEvent;
	private DamageCause cause;

	public DeathEvent(StandardPlayer player) {
		this.player = player;

		if (player.getLastDamageCause() != null) {
			damageEvent = player.getLastDamageCause();
			cause = damageEvent.getCause();
		}
	}

	private void log(DeathType type) {
		String typeString;

		switch (type) {
		case SUICIDE:
			typeString = "suicide";
			break;
		case FALL:
			typeString = "fall";
			break;
		case FIRE:
			typeString = "fire";
			break;
		case LAVA:
			typeString = "lava";
			break;
		case EXPLOSION:
			typeString = "explosion";
			break;
		case CACTUS:
			typeString = "cactus";
			break;
		case SUFFOCATION:
			typeString = "suffocation";
			break;
		case DROWNING:
			typeString = "drowning";
			break;
		case STARVATION:
			typeString = "starvation";
			break;
		case MAGIC:
			typeString = "magic";
			break;
		case PVP_LOG:
			typeString = "pvp_log";
			break;
		case OTHER:
			typeString = "other";
			break;
		default:
			typeString = "other";
			break;
		}
	}

	public void log() {
		if (cause == null) {
			if (player.hasPvpLogged()) {
				log(DeathType.PVP_LOG);
			} else {
				log(DeathType.SUICIDE);
			}
		} else if (damageEvent instanceof EntityDamageByEntityEvent) {
			LivingEntity livingDamager = MiscUtil.getLivingEntityFromDamageEvent(damageEvent);

			if (cause.equals(DamageCause.ENTITY_EXPLOSION)) {
				log(DeathType.EXPLOSION);
			} else {
				log(DeathType.SUICIDE);
			}
		} else if (damageEvent instanceof EntityDamageByBlockEvent) {
			EntityDamageByBlockEvent lastDamageByBlockEvent = (EntityDamageByBlockEvent) damageEvent;
			Block damager = lastDamageByBlockEvent.getDamager();

			if (cause.equals(DamageCause.CONTACT)) {
				if (damager.getType() == Material.CACTUS) {
					log(DeathType.CACTUS);
				} else {
					log(DeathType.OTHER);
				}
			} else if (cause.equals(DamageCause.LAVA)) {
				log(DeathType.LAVA);
			} else if (cause.equals(DamageCause.VOID)) {
				log(DeathType.VOID);
			} else {
				log(DeathType.OTHER);
			}
		} else {
			if (cause.equals(DamageCause.FIRE)) {
				log(DeathType.FIRE);
			} else if (cause.equals(DamageCause.FIRE_TICK)) {
				log(DeathType.FIRE);
			} else if (cause.equals(DamageCause.SUFFOCATION)) {
				log(DeathType.SUFFOCATION);
			} else if (cause.equals(DamageCause.DROWNING)) {
				log(DeathType.DROWNING);
			} else if (cause.equals(DamageCause.STARVATION)) {
				log(DeathType.STARVATION);
			} else if (cause.equals(DamageCause.FALL)) {
				log(DeathType.FALL);
			} else if (cause.equals(DamageCause.MAGIC)) {
				log(DeathType.MAGIC);
			} else {
				log(DeathType.SUICIDE);
			}
		}
	}
}
