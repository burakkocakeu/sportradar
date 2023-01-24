package com.sportradar.dto;

import java.util.UUID;

public class Team {
    private UUID id = UUID.randomUUID();
    private String name;
    private long score;
    public Team(String name) {
        this.name = name;
    }
    public Team(String name, long score) {
        this.name = name;
        this.score = score;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }
}
