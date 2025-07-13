package xyz.emirdev.emirnicks;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.Lamp;
import revxrsal.commands.bukkit.BukkitLamp;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import xyz.emirdev.emirnicks.commands.NickCommand;
import xyz.emirdev.emirnicks.commands.UnNickCommand;
import xyz.emirdev.emirnicks.events.PlayerEJoinEvent;
import xyz.emirdev.emirnicks.events.PlayerLeaveEvent;
import xyz.emirdev.emirnicks.events.PlayerPreLoginEvent;
import xyz.emirdev.emirnicks.nick.NickManager;
import xyz.emirdev.emirnicks.parameters.GroupParameterType;

import java.util.List;

public final class EmirNicks extends JavaPlugin {
    private static EmirNicks instance;
    private static LuckPerms luckPerms;
    private static NickManager nickManager;

    public static EmirNicks get() {
        return instance;
    }

    public static LuckPerms getLuckPerms() {
        return luckPerms;
    }

    public static NickManager getNickManager() {
        return nickManager;
    }

    @Override
    public void onEnable() {
        instance = this;
        luckPerms = LuckPermsProvider.get();
        nickManager = new NickManager();

        Lamp<BukkitCommandActor> lamp = BukkitLamp.builder(this)
                .parameterTypes(p -> {
                    p.addParameterType(Group.class, new GroupParameterType());
                })
                .build();

        List.of(
                new NickCommand(),
                new UnNickCommand()).forEach(lamp::register);

        List.of(
                new PlayerPreLoginEvent(),
                new PlayerEJoinEvent(),
                new PlayerLeaveEvent()).forEach(e -> Bukkit.getPluginManager().registerEvents(e, this));

    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
