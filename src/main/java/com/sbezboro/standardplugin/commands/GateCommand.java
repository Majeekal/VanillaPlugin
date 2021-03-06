package com.sbezboro.standardplugin.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import com.sbezboro.standardplugin.VanillaPlugin;
import com.sbezboro.standardplugin.model.Gate;
import com.sbezboro.standardplugin.model.StandardPlayer;

public class GateCommand extends BaseCommand {

	public GateCommand(VanillaPlugin plugin) {
		super(plugin, "gate");
	}

	@Override
	public boolean handle(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			showUsageInfo(sender);
			return false;
		}

		if (args[0].equalsIgnoreCase("create")) {
			if (sender instanceof ConsoleCommandSender) {
				showPlayerOnlyMessage(sender);
				return false;
			}

			StandardPlayer player = plugin.getStandardPlayer(sender);
			if (args.length == 1) {
				sender.sendMessage("Usage: /" + name + " create <name> [displayName]");
				return false;
			} else if (args.length == 2) {
				plugin.getGateStorage().createGate(args[1], null, player.getLocation());

				player.sendMessage("Gate \"" + args[1] + "\" created!");
			} else {
				String displayName = StringUtils.join(args, " ", 2, args.length);
				plugin.getGateStorage().createGate(args[1], displayName, player.getLocation());

				player.sendMessage("Gate \"" + args[1] + "\" (" + displayName + ") created!");
			}
		} else if (args[0].equalsIgnoreCase("delete")) {
			if (args.length == 1) {
				sender.sendMessage("Usage: /" + name + " delete <name>");
				return false;
			}

			if (args.length == 2) {
				Gate gate = plugin.getGateStorage().getGate(args[1]);
				if (gate == null) {
					sender.sendMessage("Gate \"" + args[1] + "\" does not exist.");
				} else {
					plugin.getGateStorage().removeGate(gate);
					sender.sendMessage("Gate \"" + args[1] + "\" removed!");
				}
			} else {
				sender.sendMessage("Usage: /" + name + " delete <name>");
				return false;
			}
		} else if (args[0].equalsIgnoreCase("link")) {
			if (args.length == 1) {
				sender.sendMessage("Usage: /" + name + " link <gate1> <gate2>");
				return false;
			}

			if (args.length == 3) {
				Gate source = plugin.getGateStorage().getGate(args[1]);
				Gate target = plugin.getGateStorage().getGate(args[2]);

				if (source == null) {
					sender.sendMessage("Gate \"" + args[1] + "\" does not exist.");
				} else if (target == null) {
					sender.sendMessage("Gate \"" + args[2] + "\" does not exist.");
				} else {
					source.setTarget(target);
					sender.sendMessage("Gate \"" + args[1] + "\" linked to \"" + args[2] + "\"");
				}
			} else {
				sender.sendMessage("Usage: /" + name + " link <gate1> <gate2>");
				return false;
			}
		} else if (args[0].equalsIgnoreCase("list")) {
			if (args.length == 1) {
				for (Gate gate : plugin.getGateStorage().getGates()) {
					String gateInfo = ChatColor.AQUA + gate.getIdentifier() + ChatColor.WHITE;
					if (gate.getDisplayName() != null) {
						gateInfo += " - " + ChatColor.YELLOW + gate.getDisplayName() + ChatColor.WHITE;
					}

					gateInfo += " (" + gate.getLocation().getBlockX() + ", " + gate.getLocation().getBlockY() + ", " + gate.getLocation().getBlockZ() + ")";

					if (gate.getTarget() != null) {
						gateInfo += " linked to " + ChatColor.AQUA + gate.getTarget().getIdentifier();
					}

					sender.sendMessage(gateInfo);
				}
			} else {
				sender.sendMessage("Usage: /" + name + " list");
				return false;
			}
		} else if (args[0].equalsIgnoreCase("tp")) {
			if (sender instanceof ConsoleCommandSender) {
				showPlayerOnlyMessage(sender);
				return false;
			}

			StandardPlayer player = plugin.getStandardPlayer(sender);
			if (args.length == 2) {
				Gate gate = plugin.getGateStorage().getGate(args[1]);
				if (gate == null) {
					sender.sendMessage("Gate \"" + args[1] + "\" does not exist.");
				} else {
					player.teleport(gate.getLocation());
					sender.sendMessage("TPed to \"" + args[1] + "\"");
				}
			} else {
				sender.sendMessage("Usage: /" + name + " tp <name>");
				return false;
			}
		}

		return true;
	}

	@Override
	public void showUsageInfo(CommandSender sender) {
		sender.sendMessage("Usage: /" + name + " create <name> [displayName]");
		sender.sendMessage("Usage: /" + name + " delete <name>");
		sender.sendMessage("Usage: /" + name + " link <gate1> <gate2>");
		sender.sendMessage("Usage: /" + name + " list");
		sender.sendMessage("Usage: /" + name + " tp <gate>");
	}

	@Override
	public boolean isPlayerOnly(int numArgs) {
		return false;
	}

}
