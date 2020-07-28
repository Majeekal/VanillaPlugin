package com.sbezboro.standardplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.sbezboro.http.HttpResponse;
import com.sbezboro.http.listeners.HttpRequestListener;
import com.sbezboro.standardplugin.VanillaPlugin;
import com.sbezboro.standardplugin.model.StandardPlayer;

public class RankCommand extends BaseCommand {

	public RankCommand(VanillaPlugin plugin) {
		super(plugin, "rank");
	}

	@Override
	public boolean handle(final CommandSender sender, Command command, String label, final String[] args) {
		if (args.length > 1) {
			showUsageInfo(sender);
			return false;
		}

		final StandardPlayer senderPlayer = plugin.getStandardPlayer(sender);
		final StandardPlayer player;

		if (args.length > 0) {
			player = plugin.matchPlayer(args[0]);
		} else {
			player = senderPlayer;
		}

		HttpRequestListener listener = new HttpRequestListener() {

			@Override
			public void requestSuccess(HttpResponse response) {
				if (response.isApiSuccess()) {
					int rank = response.getInt("rank");
					String time = response.getString("time");
					String uuid = response.getString("uuid");

					StandardPlayer actualPlayer = plugin.getStandardPlayerByUUID(uuid);

					sender.sendMessage(actualPlayer.getRankDescription(actualPlayer == senderPlayer, rank));
					sender.sendMessage(actualPlayer.getTimePlayedDescription(actualPlayer == senderPlayer, time));
				} else {
					sender.sendMessage("The player " + ChatColor.AQUA + args[0] + ChatColor.RESET + " doesn't exist on the server.");
				}
			}

			@Override
			public void requestFailure(HttpResponse response) {
				sender.sendMessage(ChatColor.RED + "There was a problem getting the rank!");
			}
		};

		return true;
	}

	@Override
	public void showUsageInfo(CommandSender sender) {
		sender.sendMessage("Usage: /" + name + " [username]");
		sender.sendMessage(ChatColor.GREEN + "This command will tell you the rank of either yourself");
		sender.sendMessage(ChatColor.GREEN + "or a player you specify. ");
	}

	@Override
	public boolean isPlayerOnly(int numArgs) {
		return numArgs == 0;
	}
}
