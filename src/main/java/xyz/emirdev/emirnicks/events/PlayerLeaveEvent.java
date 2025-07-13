package xyz.emirdev.emirnicks.events;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import xyz.emirdev.emirnicks.EmirNicks;

public class PlayerLeaveEvent implements Listener {

    public void event(PlayerQuitEvent event) {
        if (EmirNicks.getNickManager().silentKicked.contains(event.getPlayer().getUniqueId()))
            event.quitMessage(null);
    }
}
