package com.sportradar.dto;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Competition implements Comparable<Competition> {
    private UUID id = UUID.randomUUID();
    private Team home;
    private Team away;
    private LocalDateTime dateTimeStarted;
    private LocalDateTime dateTimeEnded;

    public Competition() {
    }

    public Competition(Team home, Team away, LocalDateTime dateTimeStarted) {
        this.home = home;
        this.away = away;
        this.dateTimeStarted = dateTimeStarted;
    }

    public UUID getId() {
        return id;
    }

    public Optional<Team> getTeamById(UUID teamId) {
        if (home.getId().equals(teamId))
            return Optional.ofNullable(home);
        else if (away.getId().equals(teamId))
            return Optional.ofNullable(away);
        return Optional.empty();
    }

    public long getTotalScore() {
        return this.home.getScore() + this.away.getScore();
    }

    public Team getHome() {
        return home;
    }

    public void setHome(Team home) {
        this.home = home;
    }

    public Team getAway() {
        return away;
    }

    public void setAway(Team away) {
        this.away = away;
    }

    public LocalDateTime getDateTimeStarted() {
        return dateTimeStarted;
    }

    public void setDateTimeStarted(LocalDateTime dateTimeStarted) {
        this.dateTimeStarted = dateTimeStarted;
    }

    public LocalDateTime getDateTimeEnded() {
        return dateTimeEnded;
    }

    public void setDateTimeEnded(LocalDateTime dateTimeEnded) {
        this.dateTimeEnded = dateTimeEnded;
    }

    @Override
    public String toString() {
        return "{" +
                "id:" + id +
                ", " + dateTimeStarted +
                ", " + home.getName() + " " + home.getScore() +
                " - " + away.getScore() + " " + away.getName() +
                ", FT:" + dateTimeEnded +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Competition that = (Competition) o;
        return Objects.equals(home, that.home) && Objects.equals(away, that.away) && Objects.equals(dateTimeEnded, that.dateTimeEnded);
    }

    @Override
    public int hashCode() {
        return Objects.hash(home, away, dateTimeEnded);
    }

    @Override
    public int compareTo(Competition o) {
        if (this.getTotalScore() == o.getTotalScore()) {
            return o.getDateTimeStarted().compareTo(this.getDateTimeStarted());
        }
        return (int) (o.getTotalScore() - this.getTotalScore());
    }
}
