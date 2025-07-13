package xyz.emirdev.echoname.commands;

import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import xyz.emirdev.echoname.EchoName;
import xyz.emirdev.echoname.Utils;

public class UnNickCommand {

    @Command("unnick")
    @CommandPermission("echoname.nick")
    public void unnick(Player player) {
        if (!EchoName.getNickManager().isNicked(player)) {
            Utils.sendError(player, "You are not nicked.");
            return;
        }

        EchoName.getNickManager().unNick(player);

        Utils.sendMessage(player, "<#00eeee>You are no longer nicked.");
    }
}
