package xyz.emirdev.emirnicks;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.exception.SendableException;

public class ENCommandException extends SendableException {
    public Object[] args;

    public ENCommandException(String message, Object... args) {
        super(message);
        this.args = args;
    }

    public void sendTo(@NotNull CommandActor actor) {
        if (!this.getMessage().isEmpty()) {
            actor.error(Utils.convertComponentToLegacyString(
                    Utils.format(getMessage(), args)
            ));
        }
    }
}