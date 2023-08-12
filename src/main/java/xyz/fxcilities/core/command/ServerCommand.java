package xyz.fxcilities.core.command;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import xyz.fxcilities.core.Core;
import xyz.fxcilities.core.collections.expiringmap.ExpiringMap;
import xyz.fxcilities.core.logging.Chat;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Represents a command for a server
 *
 * <p>Example:
 *
 * <pre>{@code
 * public class MyCommand extends ServerCommand {
 *
 *     public MyCommand() {
 *         super("hello", "says hello world", "/hello", true, Arrays.asList("helloworld", "world")); // label, description, usage, playerOnly, aliases
 *
 *         // Optional
 *         setCooldownDuration(5, TimeUnit.SECONDS); // Five second cooldown, this line is optional.
 *     }
 *
 *     @Override
 *     public void onCommand() {
 *         say("This is my command!"); // Sends a message to the player
 *         say(false, "&aHello world!"); // false to not show the prefix of the plugin. See {@link Core#getPrefix}
 *     }
 * }
 * }</pre>
 */
public abstract class ServerCommand extends BukkitCommand {

    private final boolean playerOnly;
    protected CommandSender sender;
    protected String[] args;

    public List<ServerSubCommand> subCommands = new ArrayList<>();
    public List<String> tabCompleteArgs = new ArrayList<>();

    private long cooldownDuration = 30;
    private TimeUnit cooldownTimeUnit = TimeUnit.SECONDS;

    protected ExpiringMap<UUID, Long> cooldownMap =
            ExpiringMap.builder().expiration(cooldownDuration, cooldownTimeUnit).build();

    /**
     * Create a server command. Registers when initialization of command.
     *
     * @param label This is the name of the command, and is what you type followed by slash in chat
     * @param description The description of the command
     * @param usage The usage message of a command. This never gets used because in {@link #execute}
     *     it always returns true, but can be used manually
     * @param playerOnly Set to true if you only want players to be able to run the command
     * @param aliases A list of command aliases
     * @see #onCommand
     */
    public ServerCommand(
            String label,
            String description,
            String usage,
            boolean playerOnly,
            final List<String> aliases) {
        super(label, description, usage, aliases);
        this.playerOnly = playerOnly;
        Core.getInstance().commands.add(this);
    }

    public ServerCommand(String label, String description, String usage, boolean playerOnly) {
        this(label, description, usage, playerOnly, Collections.emptyList());
    }

    public ServerCommand(String label, boolean playerOnly) {
        this(label, "", "/" + label, playerOnly, Collections.emptyList());
    }

    public ServerCommand(String label) {
        this(label, false);
    }

    /**
     * Registeres a sub command
     *
     * @param subCommand The sub command
     */
    public void registerSub(ServerSubCommand subCommand) {
        this.subCommands.add(subCommand);
    }

    /**
     * This is an abstract function that is called whenever the command is run. Must be overrided.
     */
    public abstract void onCommand();

    public void setCooldownDuration(long duration, TimeUnit timeUnit) {
        this.cooldownDuration = duration;
        this.cooldownTimeUnit = timeUnit;
        cooldownMap.setExpiration(cooldownDuration, cooldownTimeUnit);
    }

    /**
     * Adds arguments for tab completion.
     *
     * @param args The arguments to add
     */
    public void addTabCompleteArgs(String... args) {
        this.tabCompleteArgs.addAll(Arrays.asList(args));
    }

    private String addPrefix(String message) {
        return message.replace("{PREFIX}", Core.getInstance().getPrefix());
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        populate(sender, args);

        if (playerOnly && !(sender instanceof Player)) {
            return returnSay(false, addPrefix(Core.instance.notAPlayerMessage));
        }

        if (cooldownDuration > 0 && isPlayer()) {
            Player player = (Player) this.sender;

            long lastCommandRun = cooldownMap.getOrDefault(player.getUniqueId(), 0L);
            long difference =
                    cooldownTimeUnit.convert(
                            (System.currentTimeMillis() - lastCommandRun), TimeUnit.MILLISECONDS);
            if (difference <= cooldownDuration) {
                String remainingTime =
                        (cooldownDuration - difference) + formattedTimeUnit(cooldownTimeUnit);
                return returnSay(
                        false,
                        addPrefix(Core.instance.onCooldownMessage)
                                .replace("{TIME}", remainingTime));
            }

            cooldownMap.put(player.getUniqueId(), System.currentTimeMillis());
        }

        if (args.length >= 1) {
            for (ServerSubCommand subCommand : this.subCommands) {
                if (subCommand.label.equalsIgnoreCase(args[0])
                        || subCommand.aliases.contains(args[0].toLowerCase())) {
                    subCommand.onCommand(Arrays.copyOfRange(args, 1, args.length));
                    return true;
                }
            }
        }

        onCommand();
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args)
            throws IllegalArgumentException {
        if (!(sender instanceof Player)) return null;
        populate(sender, args);

        if (args.length == 1) {
            List<String> tabComplete = new ArrayList<>();
            for (ServerSubCommand subCommand : this.subCommands) {
                if (subCommand.label.startsWith(args[0]) || subCommand.aliases.contains(args[0])) {
                    tabComplete.add(subCommand.label);
                }
                if (subCommand.label.equalsIgnoreCase(args[0])) {
                    tabComplete.addAll(subCommand.tabCompleteArgs);
                }
            }

            tabComplete.add(getLabel());
            tabComplete.addAll(this.tabCompleteArgs);

            return tabComplete;
        }
        return Collections.emptyList();
    }

    /**
     * Send a message to the player
     *
     * @param withPrefix If the prefix should be sent before the message
     * @param message The message to send to the player
     */
    protected void say(boolean withPrefix, String message) {
        Chat.say(withPrefix, this.sender, message);
    }

    protected void say(String message) {
        say(true, message);
    }

    protected final boolean isPlayer() {
        return this.sender instanceof Player;
    }

    public CommandSender getSender() {
        return this.sender;
    }

    private boolean returnSay(boolean withPrefix, String message) {
        say(withPrefix, message);
        return true; // To avoid the usage message being sent
    }

    private void populate(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
    }

    /**
     * @author
     *     http://www.java2s.com/example/java-utility-method/timeunit-convert/tostring-timeunit-unit-13d37.html
     */
    private String formattedTimeUnit(TimeUnit unit) {
        switch (unit) {
            case HOURS:
            case DAYS:
            case MINUTES:
            case SECONDS:
                return unit.toString().substring(0, 1).toLowerCase();
            case MILLISECONDS:
                return "ms";
            case MICROSECONDS:
                return "micros";
            case NANOSECONDS:
                return "ns";
            default:
                return "";
        }
    }
}
