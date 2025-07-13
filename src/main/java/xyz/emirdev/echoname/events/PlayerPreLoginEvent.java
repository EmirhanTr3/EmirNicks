package xyz.emirdev.echoname.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import xyz.emirdev.echoname.EchoName;
import xyz.emirdev.echoname.nick.Nick;

import java.util.UUID;

public class PlayerPreLoginEvent implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void event(AsyncPlayerPreLoginEvent event) {
        UUID uuid = event.getPlayerProfile().getId();

        if (EchoName.getNickManager().isNicked(uuid)) {
            Nick nick = EchoName.getNickManager().getNick(uuid);

            EchoName.getNickManager().getModifiedProfile(event.getPlayerProfile(), nick)
                    .thenAccept(event::setPlayerProfile);

            if (nick.getUUID().equals(nick.getOriginalProfile().getId()))
                EchoName.getNickManager().addPrefixes(uuid, nick.getGroup());
        }
    }
}
