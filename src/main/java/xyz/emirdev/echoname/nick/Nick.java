package xyz.emirdev.echoname.nick;

import com.destroystokyo.paper.profile.PlayerProfile;
import net.luckperms.api.model.group.Group;

import java.util.UUID;

import org.bukkit.entity.Player;

public class Nick {
    private final Player player;
    private final String name;
    private final UUID uuid;
    private final Group group;
    private final boolean usingTargetSkin;
    private PlayerProfile originalProfile;

    public Nick(Player player, String name, UUID uuid, Group group, boolean usingTargetSkin) {
        this.player = player;
        this.name = name;
        this.uuid = uuid;
        this.group = group;
        this.usingTargetSkin = usingTargetSkin;
    }

    public Player getPlayer() {
        return player;
    }

    public String getName() {
        return name;
    }

    public UUID getUUID() {
        return uuid;
    }

    public Group getGroup() {
        return group;
    }

    public boolean isUsingTargetSkin() {
        return usingTargetSkin;
    }

    public PlayerProfile getOriginalProfile() {
        return originalProfile;
    }

    public void setOriginalProfile(PlayerProfile originalProfile) {
        this.originalProfile = originalProfile;
    }
}
