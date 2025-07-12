package xyz.emirdev.emirnicks.commands;

import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import xyz.emirdev.emirnicks.EmirNicks;
import xyz.emirdev.emirnicks.Utils;

public class UnNickCommand {

    @Command("unnick")
    @CommandPermission("emirnicks.nick")
    public void unnick(Player player) {
        if (!EmirNicks.getNickManager().isNicked(player)) {
            Utils.sendError(player, "You are not nicked.");
            return;
        }

        EmirNicks.getNickManager().unNick(player);

        Utils.sendMessage(player, "<#00eeee>You are no longer nicked.");
    }
}