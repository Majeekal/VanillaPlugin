package com.sbezboro.standardplugin.managers;

import java.time.*;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.World.Environment;
import org.bukkit.scheduler.BukkitRunnable;

import com.sbezboro.standardplugin.VanillaPlugin;
import com.sbezboro.standardplugin.model.StandardPlayer;
import com.sbezboro.standardplugin.model.Title;
import com.sbezboro.standardplugin.persistence.storages.EndResetStorage;
import com.sbezboro.standardplugin.tasks.EndResetCheckTask;
import com.sbezboro.standardplugin.tasks.EndResetTask;
import com.sbezboro.standardplugin.util.MiscUtil;

public class EndResetManager extends BaseManager {
	private EndResetStorage storage;

	private EndResetCheckTask endResetCheckTask;
	
	private World newEndWorld;

	public EndResetManager(VanillaPlugin plugin, EndResetStorage storage) {
		super(plugin);
		
		this.storage = storage;
		
		if (plugin.isEndResetEnabled()) {
			plugin.getLogger().info("End resets enabled");
			
			linkNewEndWorld();
			
			if (storage.getNextReset() == 0) {
				scheduleNextEndReset(false);
			}
			
			if (isEndResetScheduled()) {
				endResetCheckTask = new EndResetCheckTask(plugin);
				endResetCheckTask.runTaskTimerAsynchronously(plugin, 1200, 1200);
				
				plugin.getLogger().info("End reset scheduled to be on "
						+ MiscUtil.friendlyTimestamp(storage.getNextReset(), "America/New_York"));
			} else {
				plugin.getLogger().info("No end resets scheduled since the Ender Dragon is still alive");
			}
		} else {
			plugin.getLogger().info("End resets disabled");
		}
	}
	
	public void linkNewEndWorld() {
		WorldCreator creator = new WorldCreator(VanillaPlugin.NEW_END_WORLD_NAME);
		creator.environment(Environment.THE_END);
		newEndWorld = plugin.getServer().createWorld(creator);
		newEndWorld.setKeepSpawnInMemory(false);
	}

	public void resetEnd() {
		if (plugin.isEndResetEnabled()) {
			World overworld = plugin.getServer().getWorld(VanillaPlugin.OVERWORLD_NAME);
			
			new EndResetTask(plugin, overworld).runTask(plugin);
		} else {
			plugin.getLogger().severe("resetEnd() called when end resets aren't enabled!");
		}
	}
	
	public void setDragonSlayer(final StandardPlayer player, boolean broadcast) {
		StandardPlayer oldDragonSlayer = storage.getDragonSlayer();
		if (oldDragonSlayer != null) {
			oldDragonSlayer.removeTitle(Title.DRAGON_SLAYER);
		}
		if (player != null) {
			player.addTitle(Title.DRAGON_SLAYER);
			if (broadcast) {
				new BukkitRunnable() {
					@Override
					public void run() {
						VanillaPlugin.broadcast(String.format("%s%s%s has received the title %sDragon Slayer%s!",
								ChatColor.AQUA, player.getDisplayName(true), ChatColor.BLUE, ChatColor.GOLD, ChatColor.BLUE));
					}
				}.runTaskLater(plugin, 200);
			}
		} else if (broadcast) {
			new BukkitRunnable() {
				@Override
				public void run() {
					VanillaPlugin.broadcast(String.format("%sThe title %sDragon Slayer%s was not awarded to anybody this time.",
							ChatColor.BLUE, ChatColor.GOLD, ChatColor.BLUE));
				}
			}.runTaskLater(plugin, 200);
		}
		
		storage.setDragonSlayer(player);
	}
	
	public void scheduleNextEndReset(boolean broadcast) {
		if (!plugin.isEndResetEnabled()) {
			return;
		}
		
		final long nextReset = decideNextEndReset();
		
		storage.setNextReset(nextReset);
		storage.setDragonAlive(false);
		
		if (endResetCheckTask == null) {
			endResetCheckTask = new EndResetCheckTask(plugin);
			endResetCheckTask.runTaskTimerAsynchronously(plugin, 1200, 1200);
		}
		
		if (broadcast) {
			new BukkitRunnable() {
				
				@Override
				public void run() {
					VanillaPlugin.broadcast(
							String.format("%s%sThe Ender Dragon has been slain!", ChatColor.BLUE, ChatColor.BOLD)
					);
				}
			}.runTaskLater(plugin, 100);
		}
	}
	
	private long decideNextEndReset() {
		// 1 (Monday) to 7 (Sunday)
		// EST timezone
		int dayOfWeekend = decideDayOfWeekend();
		double hourOfDay = decideHourOfDay(dayOfWeekend);

		DayOfWeek dayOfWeek = ZonedDateTime.now(ZoneId.of("America/New_York")).getDayOfWeek();
		int daysFromNow = 12 - dayOfWeek.getValue() + dayOfWeekend;

		long time = System.currentTimeMillis() + daysFromNow * 86400000;
		time = (time / 86400000) * 86400000; // Round down to 00:00 GMT
		time += Math.round(hourOfDay * 3600000);

		return time;
	}
	
	private int decideDayOfWeekend() {
		// Find random day of weekend (Fri-Sun)
		double value = Math.random();

		if (value > 3.0 / 5.0) {
			return 2; // Sunday 40%
		} else if (value > 1.0 / 5.0) {
			return 1; // Saturday 40%
		} else{
			return 0; // Friday 20%
		}
	}
	
	private double decideHourOfDay(int dayOfWeekend) {
		// Using inverse transformation, find a random GMT hour with peak times being more likely
		double value = Math.random();
		
		switch (dayOfWeekend) {
			case 0: // Fri
				return 15.0 + Math.sqrt(81.0 * value);

			case 1: // Sat
				if (value < 5.0 / 9.0) {
					return 9.0 - 1.8 * Math.sqrt(-45.0 * value + 25.0);
				} else {
					return 9.0 + 7.5 * Math.sqrt(9.0 * value - 5.0);
				}

			case 2: // Sun
				if (value < 5.0 / 8.0) {
					return 9.0 - 1.8 * Math.sqrt(-40.0 * value + 25.0);
				} else {
					return 9.0 + 5.0 * Math.sqrt(12.0 * value - 7.5);
				}

		default: // Should never happen
			return 0.0;
		}
	}
	
	public World getNewEndWorld() {
		return newEndWorld;
	}
	
	public boolean isEndResetScheduled() {
		return plugin.isEndResetEnabled() && (
				storage.getNextReset() + 60000 > System.currentTimeMillis() ||
				!storage.isDragonAlive()
		);
	}

}
