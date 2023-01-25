package com.sportradar.utils;

import com.sportradar.exception.ScoreboardException;

public final class Assertions {
    public static void assertNull(Object o, String message) {
        if (o != null)
            throw new ScoreboardException(message);
    }

    public static void assertFalse(boolean expression, String message) {
        if (expression)
            throw new ScoreboardException(message);
    }
}
