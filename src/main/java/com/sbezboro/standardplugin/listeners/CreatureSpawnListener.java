package com.sbezboro.standardplugin.listeners;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import com.sbezboro.standardplugin.VanillaPlugin;
import com.sbezboro.standardplugin.util.MiscUtil;

import java.util.HashSet;
import java.util.Set;

public class CreatureSpawnListener extends EventListener implements Listener {
	@SuppressWarnings("serial")
	private static final Set<EntityType> CONTROLLED_ENTITIES = new HashSet<EntityType>() {{
		add(EntityType.COW);
		add(EntityType.CHICKEN);
		add(EntityType.PIG);
		add(EntityType.SHEEP);
		add(EntityType.VILLAGER);
	}};

	public CreatureSpawnListener(VanillaPlugin plugin) {
		super(plugin);
	}

	@EventHandler(ignoreCancelled = true)
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		Entity entity = event.getEntity();
		World world = entity.getLocation().getWorld();
		
		// Only allow withers to be spawned in the end
		if (entity.getType() == EntityType.WITHER) {
			if (world.getEnvironment() == Environment.THE_END) {
				plugin.getLogger().info("Wither spawned at " + MiscUtil.locationFormat(entity.getLocation()) + " in the end");
			} else if(world.getEnvironment() == Environment.NETHER){
				plugin.getLogger().info("Wither spawned at " + MiscUtil.locationFormat(entity.getLocation()) + " in the nether");
			} else {
				event.setCancelled(true);
				return;
			}
		}

		/* Temprorarily disable
		if (CONTROLLED_ENTITIES.contains(entity.getType())) {
			Location location = entity.getLocation();
			Chunk chunk = location.getChunk();

			int totalInChunk = 0;

			for (Entity otherEntity : chunk.getEntities()) {
				if (otherEntity.getType() == entity.getType()) {
					totalInChunk++;
				}

				if (totalInChunk > plugin.getAnimalChunkCap()) {
					event.setCancelled(true);
					return;
				}
			}
		}
		*/
	}

}
