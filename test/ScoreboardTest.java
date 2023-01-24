import com.sportradar.dto.Competition;
import com.sportradar.dto.Scoreboard;
import com.sportradar.dto.Team;
import com.sportradar.enums.Score;
import com.sportradar.service.ScoreboardService;
import com.sportradar.service.impl.ScoreboardServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ScoreboardTest {

    private ScoreboardService service;
    private List<String> teamNameList;
    private LocalDateTime now;

    @Before
    public void setup() {
        service = new ScoreboardServiceImpl(new Scoreboard());
        teamNameList = new ArrayList<>(List.of("Mexico", "Canada",
                "Spain", "Brazil",
                "Germany", "France",
                "Uruguay", "Italy",
                "Argentina", "Australia"));
        now = LocalDateTime.now();
    }

    @Test
    public void empty_ScoreboardTest() {
        assertNotNull(service.getSummary());
        assertEquals(0, service.getSummary().size());
    }

    @Test
    public void singleCompetition_ScoreboardTest() {
        service.getScoreboard().setCompetitionList(new ArrayList<>(List.of(new Competition())));

        assertEquals(1, service.getSummary().size());
        assertNull(service.getSummary().get(0).getDateTimeStarted());
    }

    @Test
    public void singleCompetitionTeamNameAndScore_ScoreboardTest() {
        service.getScoreboard().setCompetitionList(new ArrayList<>(List.of(
                new Competition(new Team(teamNameList.get(0)), new Team(teamNameList.get(1)), now))));

        assertNotNull(service.getSummary().get(0).getDateTimeStarted());
        assertNotNull(service.getSummary().get(0).getHome());
        assertEquals(teamNameList.get(0), service.getSummary().get(0).getHome().getName());
        assertEquals(0, service.getSummary().get(0).getHome().getScore());
    }

    @Test
    public void singleCompetitionTeamTotalScores_ScoreboardTest() {
        service.getScoreboard().setCompetitionList(new ArrayList<>(List.of(
                new Competition(new Team(teamNameList.get(0), 2), new Team(teamNameList.get(1), 3), now))));

        assertEquals(5, service.getSummary().get(0).getTotalScore());
    }

    @Test
    public void multipleCompetitionTeamScoresWithOrdering_ScoreboardTest() {
        service.getScoreboard().setCompetitionList(new ArrayList<>(List.of(
                new Competition(new Team(teamNameList.get(0), 0), new Team(teamNameList.get(1), 5), now.plusMinutes(5)),
                new Competition(new Team(teamNameList.get(2), 10), new Team(teamNameList.get(3), 2), now.plusMinutes(10)),
                new Competition(new Team(teamNameList.get(4), 2), new Team(teamNameList.get(5), 2), now.plusMinutes(15)),
                new Competition(new Team(teamNameList.get(6), 6), new Team(teamNameList.get(7), 6), now.plusMinutes(20)),
                new Competition(new Team(teamNameList.get(8), 3), new Team(teamNameList.get(9), 1), now.plusMinutes(25))
                )));

        List<Competition> competitionList = service.getSummary();

        assertEquals(12, competitionList.get(0).getTotalScore());
        assertEquals(teamNameList.get(6), competitionList.get(0).getHome().getName());

        assertEquals(12, competitionList.get(1).getTotalScore());
        assertEquals(teamNameList.get(2), competitionList.get(1).getHome().getName());

        assertEquals(5, competitionList.get(2).getTotalScore());
        assertEquals(teamNameList.get(0), competitionList.get(2).getHome().getName());

        assertEquals(4, competitionList.get(3).getTotalScore());
        assertEquals(teamNameList.get(8), competitionList.get(3).getHome().getName());

        assertEquals(4, competitionList.get(4).getTotalScore());
        assertEquals(teamNameList.get(4), competitionList.get(4).getHome().getName());

        System.out.println(competitionList);
    }

    @Test
    public void multiple_StartNewGame_ScoreboardTest() {
        service.startNewGame(teamNameList.get(0), teamNameList.get(1), now);
        service.startNewGame(teamNameList.get(2), teamNameList.get(3), now);

        assertEquals(2, service.getScoreboard().getCompetitionList().size());
        assertEquals(0, service.getScoreboard().getCompetitionList().get(0).getTotalScore());
        assertEquals(0, service.getScoreboard().getCompetitionList().get(1).getTotalScore());
        assertNull(service.getScoreboard().getCompetitionList().get(1).getDateTimeEnded());
    }

    @Test
    public void single_FinishGame_ScoreboardTest() throws Exception {
        service.startNewGame(teamNameList.get(0), teamNameList.get(1), now);
        service.startNewGame(teamNameList.get(2), teamNameList.get(3), now);
        service.finishGame(service.getScoreboard().getCompetitionList().get(0).getId());


        assertNotNull(service.getScoreboard().getCompetitionList().get(0).getDateTimeEnded());
        assertNull(service.getScoreboard().getCompetitionList().get(1).getDateTimeEnded());

        System.out.println(service.getScoreboard().getCompetitionList());
    }

    @Test
    public void single_UpdateScore_ScoreboardTest() throws Exception {
        service.startNewGame(teamNameList.get(0), teamNameList.get(1), now);
        Competition competition = service.getScoreboard().getCompetitionList().get(0);
        service.updateScore(competition.getId(), competition.getHome().getId(), Score.GOAL);
        service.updateScore(competition.getId(), competition.getAway().getId(), Score.GOAL);
        service.updateScore(competition.getId(), competition.getAway().getId(), Score.GOAL);
        service.updateScore(competition.getId(), competition.getAway().getId(), Score.CANCEL);

        assertEquals(2, competition.getTotalScore());
        assertEquals(1, competition.getHome().getScore());
        assertEquals(1, competition.getAway().getScore());
    }

    @Test
    public void multiple_GetSummary_ScoreboardTest() throws Exception {
        service.startNewGame(teamNameList.get(0), teamNameList.get(1), now);
        service.startNewGame(teamNameList.get(2), teamNameList.get(3), now.plusHours(1));
        Competition competition1 = service.getScoreboard().getCompetitionList().get(0);
        Competition competition2 = service.getScoreboard().getCompetitionList().get(1);

        service.updateScore(competition1.getId(), competition1.getHome().getId(), Score.GOAL);
        service.updateScore(competition1.getId(), competition1.getAway().getId(), Score.GOAL);
        service.updateScore(competition2.getId(), competition2.getHome().getId(), Score.GOAL);
        service.updateScore(competition2.getId(), competition2.getAway().getId(), Score.GOAL);

        List<Competition> competitionList = service.getSummary();
        assertEquals(2, competitionList.get(0).getTotalScore());
        assertEquals(teamNameList.get(2), competitionList.get(0).getHome().getName());
        assertEquals(2, competitionList.get(1).getTotalScore());
        assertEquals(teamNameList.get(0), competitionList.get(1).getHome().getName());

        System.out.println(competitionList);
    }

}
