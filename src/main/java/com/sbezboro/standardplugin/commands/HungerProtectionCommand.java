package com.sbezboro.standardplugin.commands;

import com.sbezboro.standardplugin.VanillaPlugin;
import com.sbezboro.standardplugin.model.StandardPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class HungerProtectionCommand extends BaseCommand {

    public HungerProtectionCommand(VanillaPlugin plugin) {
        super(plugin, "hunger");
    }

    @Override
    public boolean handle(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 || args.length > 3) {
            showUsageInfo(sender);
            return false;
        }

        StandardPlayer player = plugin.matchPlayer(args[0]);
        if (player == null) {
            sender.sendMessage("Player " + args[0] + " not found!");
            return true;
        }

        if (args.length == 1) {
            sender.sendMessage("Hunger protection is " + (player.isPvpProtected() ? "enabled" : "disabled") + " for " + player.getDisplayName(false));
        } else if (args.length == 2) {
            if (!args[1].equals("on") && !args[1].equals("off")) {
                showUsageInfo(sender);
                return false;
            }

            boolean enabled = args[1].equals("on");
            player.setHungerProtection(enabled);

            if (enabled) {
                sender.sendMessage("Enabled hunger protection for " + player.getDisplayName(false) + "!");
            } else {
                sender.sendMessage("Disabled hunger protection for " + player.getDisplayName(false) + "!");
            }
        }

        return true;
    }

    @Override
    public void showUsageInfo(CommandSender sender) {
        sender.sendMessage("Usage: /" + name + " <name> <on/off>");
    }

    @Override
    public boolean isPlayerOnly(int numArgs) {
        return false;
    }

}
