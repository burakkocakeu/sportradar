package com.sportradar.service.impl;

import com.sportradar.dto.Competition;
import com.sportradar.dto.Scoreboard;
import com.sportradar.dto.Team;
import com.sportradar.enums.Score;
import com.sportradar.service.ScoreboardService;
import com.sportradar.utils.Constants;

import java.time.LocalDateTime;
import java.util.Comparator;
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
    }

    @Override
    public void finishGame(UUID competitionId) throws Exception {
        scoreboard.getCompetitionList().stream()
                .filter(competition -> competition.getId().equals(competitionId)).findFirst()
                .orElseThrow(() -> new Exception(Constants.NOT_FOUND))
                .setDateTimeEnded(LocalDateTime.now());
    }

    @Override
    public void updateScore(UUID competitionId, UUID teamId, Score score) throws Exception {
        Competition competition = scoreboard.getCompetitionList().stream()
                .filter(c -> c.getId().equals(competitionId)).findFirst()
                .orElseThrow(() -> new Exception(Constants.NOT_FOUND));
        Team team = competition.getTeamById(teamId)
                .orElseThrow(() -> new Exception(Constants.NOT_FOUND));
        team.setScore(team.getScore() + score.getValue());
    }

    @Override
    public List<Competition> getSummary() {
        scoreboard.getCompetitionList()
                .sort(Comparator.comparing(Competition::getTotalScore)
                        .thenComparing(Competition::getDateTimeStarted)
                        .reversed());
        return scoreboard.getCompetitionList();
    }
}
