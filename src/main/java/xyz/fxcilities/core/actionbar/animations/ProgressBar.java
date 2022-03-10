package xyz.fxcilities.core.actionbar.animations;

import org.bukkit.scheduler.BukkitRunnable;
import xyz.fxcilities.core.Core;
import xyz.fxcilities.core.actionbar.PlayerActionBar;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProgressBar extends BukkitRunnable {
    private final PlayerActionBar actionBar;
    private final int maxBarTicks;
    private final int maxDisplayTicks;

    private int ticked = 0;
    private final String begin, middle, end;

    public ProgressBar(PlayerActionBar actionBar, int maxBarTicks, int maxDisplayTicks, String begin, String middle, String end) {
        /**
         * Example:
         *
         * new ProgressBar(actionBar, 15, 20, "&f> ", "&c▮", "&f <");
         *
         * A progress bar with those paramaters could look like:
         * > ▮▮▮▮▮▮ <
         */
        this.actionBar = actionBar;
        this.maxBarTicks = maxBarTicks;
        this.maxDisplayTicks = maxDisplayTicks;

        this.begin = begin;
        this.middle = middle;
        this.end = end;

        runTaskTimer(Core.getInstance(), 0L, 1L);
    }

    @Override
    public void run() {
        ticked++;
        if (ticked == maxDisplayTicks) {
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
