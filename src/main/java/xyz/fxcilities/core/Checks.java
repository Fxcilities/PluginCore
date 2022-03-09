package xyz.fxcilities.core;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.NoSuchElementException;

/**
 * @author Fxcilities
 */
public class Checks {
    public static <T> T nonNull(@Nullable T obj, @Nonnull String name) {
        if (obj == null) {
            throw new IllegalArgumentException(name + " cannot be null.");
        }
        return obj;
    }

    /* Basically an assertion */
    public static void check(boolean failed, String name) {
        if (failed) {
            throw new RuntimeException(name);
        }
    }

    /**
     * @author Jonathan Halterman
     */
    public static void state(boolean expression, String errorMessageFormat, Object... args) {
        if (!expression)
            throw new IllegalStateException(String.format(errorMessageFormat, args));
    }

    /**
     * @author Jonathan Halterman
     */
    public static void element(Object element, Object key) {
        if (element == null)
            throw new NoSuchElementException(key.toString());
    }
}
