package com.sportradar.enums;

public enum Score {
    GOAL(1),
    CANCEL(-1);

    private int value;

    Score(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
