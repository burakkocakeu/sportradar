package com.sportradar.dto;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard {
    private List<Competition> competitionList = new ArrayList<>();

    public Scoreboard() {
    }

    public Scoreboard(List<Competition> competition) {
        competitionList = competition;
    }

    public List<Competition> getCompetitionList() {
        return competitionList;
    }

    public void setCompetitionList(List<Competition> competitionList) {
        this.competitionList = competitionList;
    }
}
