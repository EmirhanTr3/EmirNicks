package xyz.emirdev.emirnicks.commands;

import net.luckperms.api.model.group.Group;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Optional;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import xyz.emirdev.emirnicks.EmirNicks;
import xyz.emirdev.emirnicks.Utils;
import xyz.emirdev.emirnicks.nick.Nick;

import java.util.Objects;
import java.util.UUID;

public class NickCommand {

    @Command("nick")
    @CommandPermission("emirnicks.nick")
    public void nick(Player player, String name, Group group, @Optional String uuid, @Optional boolean useTargetSkin) {
        if (EmirNicks.getNickManager().isNicked(player)) {
            Utils.sendError(player, "You are already nicked.");
            return;
        }

        if (!name.matches("[A-z0-9_]{1,16}")) {
            Utils.sendError(player, "Nickname is not a valid username.");
            return;
        }

        if (uuid != null && !uuid.matches("[A-f0-9]{8}-[A-f0-9]{4}-[A-f0-9]{4}-[A-f0-9]{4}-[A-f0-9]{12}")) {
            Utils.sendError(player, "Uuid is not a valid uuid.");
            return;
        }

        Nick nick = new Nick(
                player,
                name,
                UUID.fromString(Objects.requireNonNullElse(uuid, player.getUniqueId().toString())),
                group,
                Objects.requireNonNullElse(useTargetSkin, false));

        EmirNicks.getNickManager().nick(player, nick);

        Utils.sendMessage(player,
                "<#00eeee>You are now nicked as <#00ccff>{0}{1}",
                group.getCachedData().getMetaData().getPrefix() != null
                        ? Utils.convertLegacyHexToMiniMessage(group.getCachedData().getMetaData().getPrefix())
                        : "",
                name);
    }
}
