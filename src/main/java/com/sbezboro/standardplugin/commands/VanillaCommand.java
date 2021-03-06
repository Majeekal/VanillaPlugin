package com.sbezboro.standardplugin.commands;

import com.sbezboro.standardplugin.VanillaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class VanillaCommand extends BaseCommand {

	public VanillaCommand(VanillaPlugin plugin) {
		super(plugin, "vanilla");
	}

	@Override
	public boolean handle(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			showUsageInfo(sender);
			return false;
		}

		if (args[0].equalsIgnoreCase("reload")) {
			plugin.reloadPlugin();
			sender.sendMessage("Plugin reloaded");
			return true;
		}

		showUsageInfo(sender);
		return false;
	}

	@Override
	public void showUsageInfo(CommandSender sender) {
		sender.sendMessage("Usage: /" + name + " reload");
	}

	@Override
	public boolean isPlayerOnly(int numArgs) {
		return false;
	}

}
