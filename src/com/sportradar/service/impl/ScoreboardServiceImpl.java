package com.sportradar.service.impl;

import com.sportradar.dto.Competition;
import com.sportradar.dto.Scoreboard;
import com.sportradar.dto.Team;
import com.sportradar.enums.Score;
import com.sportradar.exception.ScoreboardException;
import com.sportradar.service.ScoreboardService;
import com.sportradar.utils.Assertions;
import com.sportradar.utils.Constants;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ScoreboardServiceImpl implements ScoreboardService {
    private Scoreboard scoreboard;

    public ScoreboardServiceImpl(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    @Override
    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    @Override
    public void startNewGame(String home, String away, LocalDateTime dateTimeStarted) {
        scoreboard.getCompetitionList()
                .add(new Competition(new Team(home), new Team(away), dateTimeStarted));
        System.out.println("The match has started!");
    }

    @Override
    public void finishGame(UUID competitionId) {
        Competition competition = scoreboard.getCompetitionList().stream()
                .filter(c -> c.getId().equals(competitionId)).findFirst()
                .orElseThrow(() -> new ScoreboardException(Constants.NOT_FOUND));
        Assertions.assertNull(competition.getDateTimeEnded(), Constants.MATCH_CANNOT_BE_UPDATED);
        competition.setDateTimeEnded(LocalDateTime.now());
        System.out.println("The match has finished!");
    }

    @Override
    public void updateScore(UUID competitionId, UUID teamId, Score score) {
        Competition competition = scoreboard.getCompetitionList().stream()
                .filter(c -> c.getId().equals(competitionId)).findFirst()
                .orElseThrow(() -> new ScoreboardException(Constants.NOT_FOUND));
        Team team = competition.getTeamById(teamId)
                .orElseThrow(() -> new ScoreboardException(Constants.NOT_FOUND));
        Assertions.assertNull(competition.getDateTimeEnded(), Constants.MATCH_CANNOT_BE_UPDATED);
        team.setScore(team.getScore() + score.getValue());
        System.out.println(String.format("The match has updated! %s: %s", team.getName(), team.getScore()));
    }

    @Override
    public List<Competition> getSummary() {
        Collections.sort(scoreboard.getCompetitionList());

        /** An alternative sorting to Comparable interface;
        scoreboard.getCompetitionList()
                .sort(Comparator.comparing(Competition::getTotalScore)
                        .thenComparing(Competition::getDateTimeStarted)
                        .reversed());
        */

        return scoreboard.getCompetitionList();
    }
}
