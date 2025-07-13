package xyz.emirdev.echoname.parameters;

import net.luckperms.api.model.group.Group;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.parameter.ParameterType;
import revxrsal.commands.stream.MutableStringStream;
import xyz.emirdev.echoname.ENCommandException;
import xyz.emirdev.echoname.EchoName;

public class GroupParameterType implements ParameterType<CommandActor, Group> {

    @Override
    public Group parse(@NotNull MutableStringStream input, @NotNull ExecutionContext<@NotNull CommandActor> context) {
        String name = input.readString();

        Group group = EchoName.getLuckPerms().getGroupManager().getGroup(name);

        if (group == null) throw new ENCommandException(
                "<red>Invalid group:</red> <yellow>{0}</yellow>",
                name
        );

        return group;
    }

    @Override
    public @NotNull SuggestionProvider<@NotNull CommandActor> defaultSuggestions() {
        return (context) -> EchoName.getLuckPerms().getGroupManager().getLoadedGroups().stream().map(Group::getName).toList();
    }

    @Override
    public boolean isGreedy() {
        return false;
    }
}
