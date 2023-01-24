package com.sportradar.service;

import com.sportradar.dto.Competition;
import com.sportradar.dto.Scoreboard;
import com.sportradar.enums.Score;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ScoreboardService {
    Scoreboard getScoreboard();
    void startNewGame(String home, String away, LocalDateTime dateTimeStarted);
    void finishGame(UUID competitionId) throws Exception;
    void updateScore(UUID competitionId, UUID teamId, Score score) throws Exception;
    List<Competition> getSummary();
}
