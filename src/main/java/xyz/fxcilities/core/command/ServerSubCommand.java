package xyz.fxcilities.core.command;

import java.util.List;

public abstract class ServerSubCommand {
    public final ServerCommand parent;
    public final String label, description, usage;
    public final List<String> aliases;

    public ServerSubCommand(ServerCommand parent, String label, String description, String usage, List<String> aliases) {
        this.parent = parent;
        this.label = label;
        this.description = description;
        this.usage = usage;
        this.aliases = aliases;

        parent.registerSub(this);
    }

    public abstract void onCommand();
}
