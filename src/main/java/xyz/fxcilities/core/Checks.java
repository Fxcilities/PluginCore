package xyz.fxcilities.core;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.NoSuchElementException;

/**
 * @author Fxcilities A better way of assertions
 */
public class Checks {
    /**
     * @param obj The object to check if null
     * @param name The name of the object (Example: "player")
     * @return Returns the obj parameter if not null
     * @throws IllegalArgumentException if the object is null
     */
    public static <T> T nonNull(@Nullable T obj, @Nonnull String name) {
        if (obj == null) {
            throw new IllegalArgumentException(name + " cannot be null.");
        }
        return obj;
    }

    /**
     * A way of doing assertions
     *
     * @param failed If the check failed
     * @param name Name of the check
     * @throws RuntimeException if failed was true
     */
    public static void check(boolean failed, String name) {
        if (failed) {
            throw new RuntimeException(name);
        }
    }

    /**
     * @author Jonathan Halterman
     * Taken from ExpiringMap
     */
    public static void state(boolean expression, String errorMessageFormat, Object... args) {
        if (!expression) throw new IllegalStateException(String.format(errorMessageFormat, args));
    }

    /**
     * @author Jonathan Halterman
     * Taken from ExpiringMap
     */
    public static void element(Object element, Object key) {
        if (element == null) throw new NoSuchElementException(key.toString());
    }
}
