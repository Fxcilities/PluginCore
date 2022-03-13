package xyz.fxcilities.core.command;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import xyz.fxcilities.core.Core;
import xyz.fxcilities.core.collections.expiringmap.ExpiringMap;
import xyz.fxcilities.core.logging.Chat;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


public abstract class ServerCommand extends BukkitCommand {

    private final boolean playerOnly;
    protected CommandSender sender;
    protected String[] args;

    private long cooldownDuration = 30;
    private TimeUnit cooldownTimeUnit = TimeUnit.SECONDS;

    protected ExpiringMap<UUID, Date> cooldownMap =  ExpiringMap.builder()
            .expiration(cooldownDuration, cooldownTimeUnit)
            .build();

    public ServerCommand(String label, String description, String usage, boolean playerOnly, final List<String> aliases) {
        super(label, description, usage, aliases);
        this.playerOnly = playerOnly;

        Core.getInstance().commands.add(this);
    }

    public ServerCommand(String label, boolean playerOnly) {
        this(label, "", "/" + label, playerOnly, Collections.emptyList());
    }

    public ServerCommand(String label) {
        this(label, false);
    }

    public abstract void onCommand();

    public void setCooldownDuration(long duration, TimeUnit timeUnit) {
        this.cooldownDuration = duration;
        this.cooldownTimeUnit = timeUnit;
        cooldownMap.setExpiration(cooldownDuration, cooldownTimeUnit);
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        populate(sender, args);

        if (playerOnly && !(sender instanceof Player)) {
            return returnSay(true, "&c&lYou must be a player to run this command!");
        }

        if (isPlayer()) {
            Player player = (Player) this.sender;

            long diffInMillies = new Date(System.currentTimeMillis()).getTime() - cooldownMap.getOrDefault(player.getUniqueId(), new Date(System.currentTimeMillis())).getTime();
            long difference = cooldownTimeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);

            if (difference >= cooldownDuration) {
                return returnSay(true, "&cYou are on a cooldown! You may run this command again in &l" + difference + formattedTimeUnit(cooldownTimeUnit));
            }
            cooldownMap.put(player.getUniqueId(), new Date(System.currentTimeMillis()));
        }

        onCommand();
        return true;
    }

    protected void say(boolean withPrefix, String message) {
        Chat.say(withPrefix, this.sender, message);
    }

    protected void say(String message) {
        say(true, message);
    }

    protected final boolean isPlayer() {
        return this.sender instanceof Player;
    }

    protected boolean returnSay(boolean withPrefix, String message) {
        say(withPrefix, message);
        return true; // To avoid the usage message being sent
    }

    private void populate(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
    }

    /**
     * @author http://www.java2s.com/example/java-utility-method/timeunit-convert/tostring-timeunit-unit-13d37.html
     */
    private String formattedTimeUnit(TimeUnit unit) {
        return switch (unit) {
            case HOURS, DAYS, MINUTES, SECONDS -> unit.toString().substring(0, 1).toLowerCase();
            case MILLISECONDS -> "ms";
            case MICROSECONDS -> "micros";
            case NANOSECONDS -> "ns";
        };
    }

}
