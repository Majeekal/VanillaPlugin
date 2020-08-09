package com.sbezboro.standardplugin.integrations;

import com.earth2me.essentials.IEssentials;
import com.earth2me.essentials.User;
import com.sbezboro.standardplugin.VanillaPlugin;
import com.sbezboro.standardplugin.model.StandardPlayer;

import java.util.UUID;

public class EssentialsIntegration extends PluginIntegration {
	private static final String CLASS_NAME = "com.earth2me.essentials.IEssentials";
	private static final String PLUGIN_NAME = "Essentials";
	private static IEssentials essentials;

	public static void init(VanillaPlugin plugin) {
		essentials = init(plugin, CLASS_NAME, PLUGIN_NAME);
	}

	public static User getUserByUUID(UUID uuid) {
		if (essentials == null) {
			throw new RuntimeException("Essentials API is called before Essentials is loaded.");
		}
		if (uuid == null) {
			throw new IllegalArgumentException("Economy uuid cannot be null");
		}

		return essentials.getUser(uuid);
	}

	public static boolean hasNickname(StandardPlayer player) {
		if (!enabled) {
			return false;
		}
		return getNickname(player) != null;
	}

	public static String getNickname(StandardPlayer player) {
		User user = getUserByUUID(player.getUniqueId());
		if (user != null) {
			return user.getNickname();
		}

		return player.getDisplayName();
	}

	public static double getTPS() {
		if (!enabled) {
			return 0f;
		}

		Double tps = essentials.getTimer().getAverageTPS();
		if (tps == null) {
			return 0f;
		}

		return tps;
	}

	public static boolean isPlayerMuted(StandardPlayer player) {
		if (!enabled) {
			return false;
		}

		return getUserByUUID(player.getUniqueId()).isMuted();
	}

	public static void setPlayerMuted(StandardPlayer player, boolean muted) {
		if (!enabled) {
			return;
		}
		getUserByUUID(player.getUniqueId()).setMuted(muted);
	}

	public static boolean doesPlayerIgnorePlayer(StandardPlayer first, StandardPlayer second) {
		if (!enabled) {
			return false;
		}

		if (first == null || second == null) {
			return false;
		}

		User firstUser = getUserByUUID(first.getUniqueId());
		User secondUser = getUserByUUID(second.getUniqueId());

		return firstUser.isIgnoredPlayer(secondUser);
	}
}
