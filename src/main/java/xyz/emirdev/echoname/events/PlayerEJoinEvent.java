package xyz.emirdev.echoname.events;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import xyz.emirdev.echoname.EchoName;

public class PlayerEJoinEvent implements Listener {

    public void event(PlayerJoinEvent event) {
        if (EchoName.getNickManager().silentKicked.contains(event.getPlayer().getUniqueId()))
            event.joinMessage(null);
    }
}
