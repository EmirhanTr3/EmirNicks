package xyz.emirdev.emirnicks.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import xyz.emirdev.emirnicks.EmirNicks;
import xyz.emirdev.emirnicks.nick.Nick;

import java.util.UUID;

public class PlayerPreLoginEvent implements Listener {

    @SuppressWarnings("all")
    @EventHandler()
    public void event(AsyncPlayerPreLoginEvent event) {
        UUID uuid = event.getPlayerProfile().getId();

        if (EmirNicks.getNickManager().isNicked(uuid)) {
            Nick nick = EmirNicks.getNickManager().getNick(uuid);

            EmirNicks.getNickManager().getModifiedProfile(event.getPlayerProfile(), nick).thenAccept(event::setPlayerProfile);
            EmirNicks.getNickManager().addPrefixes(uuid, nick.getGroup());
        }
    }
}
