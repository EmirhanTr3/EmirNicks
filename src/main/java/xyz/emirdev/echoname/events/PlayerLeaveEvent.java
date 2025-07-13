package xyz.emirdev.echoname.events;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import xyz.emirdev.echoname.EchoName;

public class PlayerLeaveEvent implements Listener {

    public void event(PlayerQuitEvent event) {
        if (EchoName.getNickManager().silentKicked.contains(event.getPlayer().getUniqueId()))
            event.quitMessage(null);
    }
}
