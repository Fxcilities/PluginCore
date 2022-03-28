package xyz.fxcilities.core.actionbar.animations;

import org.bukkit.scheduler.BukkitRunnable;
import xyz.fxcilities.core.Core;
import xyz.fxcilities.core.actionbar.PlayerActionBar;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Fxcilities
 * Create a progress bar animation using the {@link PlayerActionBar} wrapper.
 *
 * Example:
 *
 * A progress bar with the paramaters:
 * {@code new ProgressBar(actionBar, 15, 20, "&f> ", "&c▮", "&f <")}
 * would look like:
 * > ▮▮▮▮▮▮ <
 */
public class ProgressBar extends BukkitRunnable {
    private final PlayerActionBar actionBar;
    private final int maxBarTicks;
    private final int maxDisplayTicks;

    private int ticked = 0;
    private final String begin, middle, end;

    /**
     *
     * @param actionBar A {@link PlayerActionBar} object. Can be created with {@code new PlayerActionBar(player)}
     * @param maxBarTicks The amount of ticks the bar will animate for (descreasing once per tick).
     * @param maxDisplayTicks The maximum display ticks, if you want to show the progress bar for a few ticks after the animation ended
     * @param begin String for the opening character of the progress bar. Example: {@code "&f> "}
     * @param middle String for the animating character that represents the progress. Example: {@code "&c▮"}
     * @param end String for the closing character of the progress bar. Example: {@code "&f <"}
     */
    public ProgressBar(PlayerActionBar actionBar, int maxBarTicks, int maxDisplayTicks, String begin, String middle, String end) {
        this.actionBar = actionBar;
        this.maxBarTicks = maxBarTicks;
        this.maxDisplayTicks = maxDisplayTicks;

        this.begin = begin;
        this.middle = middle;
        this.end = end;

        runTaskTimer(Core.getInstance(), 0L, 1L);
    }

    /**
     * Start the animation
     */
    @Override
    public void run() {
        ticked++;
        if (ticked >= maxDisplayTicks) {
            this.cancel();
            return;
        }

        int progress = maxBarTicks - ticked;

        String bar = IntStream.range(0, 15 - progress)
                .mapToObj(i2 -> middle)
                .collect(Collectors.joining());

        actionBar.setBar(begin + bar + end);
        actionBar.sendBar();
    }
}
