package xyz.emirdev.emirnicks.events;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import xyz.emirdev.emirnicks.EmirNicks;

public class PlayerEJoinEvent implements Listener {

    public void event(PlayerJoinEvent event) {
        if (EmirNicks.getNickManager().silentKicked.contains(event.getPlayer().getUniqueId()))
            event.joinMessage(null);
    }
}
