package com.sbezboro.standardplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.sbezboro.standardplugin.VanillaPlugin;
import com.sbezboro.standardplugin.model.StandardPlayer;

public class UnfreezeCommand extends BaseCommand {

	public UnfreezeCommand(VanillaPlugin plugin) {
		super(plugin, "unfreeze");
	}

	@Override
	public boolean handle(CommandSender sender, Command command, String label, String[] args) {
		StandardPlayer player = plugin.getStandardPlayer(sender);
		player.teleport(player);
		player.sendMessage("You should be unfrozen!");
		return true;
	}

	@Override
	public void showUsageInfo(CommandSender sender) {
		sender.sendMessage("Usage: /" + name);
	}

	@Override
	public boolean isPlayerOnly(int numArgs) {
		return true;
	}
}
