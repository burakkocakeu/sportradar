package com.sportradar.dto;

import java.util.Objects;
import java.util.UUID;

public class Team {
    private UUID id = UUID.randomUUID();
    private String name;
    private long score;

    public Team() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(name, team.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
