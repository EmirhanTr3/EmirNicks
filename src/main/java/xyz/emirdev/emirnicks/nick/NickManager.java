package xyz.emirdev.emirnicks.nick;

import com.destroystokyo.paper.profile.PlayerProfile;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.PrefixNode;
import net.luckperms.api.node.types.SuffixNode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import xyz.emirdev.emirnicks.EmirNicks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class NickManager {
    private final Map<UUID, Nick> nickedPlayers = new HashMap<>();

    public boolean isNicked(Player player) {
        return nickedPlayers.containsKey(player.getUniqueId());
    }

    public boolean isNicked(UUID uuid) {
        return nickedPlayers.containsKey(uuid);
    }

    public Nick getNick(Player player) {
        return nickedPlayers.get(player.getUniqueId());
    }

    public Nick getNick(UUID uuid) {
        return nickedPlayers.get(uuid);
    }

    public void nick(Player player, Nick nick) {
        if (nickedPlayers.containsKey(player.getUniqueId())) throw new RuntimeException("Player is already nicked");
        if (!nick.getName().matches("[A-z0-9_]{1,16}")) throw new RuntimeException("Player nickname is not a valid username");

        nick.setOriginalProfile(player.getPlayerProfile());
        nickedPlayers.put(player.getUniqueId(), nick);

        getModifiedProfile(player.getPlayerProfile(), nick).thenAcceptAsync(profile -> {
            Bukkit.getScheduler().runTask(EmirNicks.get(), () -> {
                player.setPlayerProfile(profile);
            });
            Bukkit.getScheduler().runTaskLater(EmirNicks.get(), () -> resetPassengers(player), 10);
        });

        addPrefixes(player.getUniqueId(), nick.getGroup());
    }

    public void unNick(Player player) {
        if (!nickedPlayers.containsKey(player.getUniqueId())) throw new RuntimeException("Player is not nicked");

        Nick nick = nickedPlayers.get(player.getUniqueId());

        player.setPlayerProfile(nick.getOriginalProfile());
        Bukkit.getScheduler().runTaskLater(EmirNicks.get(), () -> resetPassengers(player), 10);

        removePrefixes(player.getUniqueId(), nick.getGroup());

        nickedPlayers.remove(player.getUniqueId());
    }

    @SuppressWarnings("all")
    public CompletableFuture<PlayerProfile> getModifiedProfile(PlayerProfile profile, Nick nick) {
        return new CompletableFuture<PlayerProfile>().completeAsync(() -> {
            profile.setName(nick.getName());

            if (nick.isUsingTargetSkin()) {
                try {
                    PlayerProfile targetProfile = Bukkit.getOfflinePlayer(nick.getName()).getPlayerProfile().update().get();
                    profile.setTextures(targetProfile.getTextures());
                } catch (InterruptedException | ExecutionException ignored) {}
            }

            return profile;
        });
    }

    public void addPrefixes(UUID uuid, Group group) {
        CachedMetaData groupMetaData = group.getCachedData().getMetaData();
        User user = EmirNicks.getLuckPerms().getUserManager().getUser(uuid);

        if (groupMetaData.getPrefix() != null) {
            user.data().add(PrefixNode.builder(groupMetaData.getPrefix(), 3112024).build());
        }
        if (groupMetaData.getSuffix() != null) {
            user.data().add(SuffixNode.builder(groupMetaData.getSuffix(), 3112024).build());
        }

        EmirNicks.getLuckPerms().getUserManager().saveUser(user);
    }

    public void removePrefixes(UUID uuid, Group group) {
        CachedMetaData groupMetaData = group.getCachedData().getMetaData();
        User user = EmirNicks.getLuckPerms().getUserManager().getUser(uuid);

        if (groupMetaData.getPrefix() != null) {
            user.data().remove(PrefixNode.builder(groupMetaData.getPrefix(), 3112024).build());
        }
        if (groupMetaData.getSuffix() != null) {
            user.data().remove(SuffixNode.builder(groupMetaData.getSuffix(), 3112024).build());
        }

        EmirNicks.getLuckPerms().getUserManager().saveUser(user);
    }

    private void resetPassengers(Player player) {
        List<Entity> passengers = player.getPassengers();
        passengers.forEach(player::removePassenger);
        Bukkit.getScheduler().runTaskLater(EmirNicks.get(), () -> {
            passengers.forEach(player::addPassenger);
        }, 5);
    }
}
