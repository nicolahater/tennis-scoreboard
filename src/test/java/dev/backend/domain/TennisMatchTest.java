package dev.backend.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class TennisMatchTest {

    private TennisPlayer player1;
    private TennisPlayer player2;
    private TennisMatch match;

    @BeforeEach
    void setUp() {
        player1 = new TennisPlayer("Player 1");
        player2 = new TennisPlayer("Player 2");
        match = new TennisMatch(player1, player2);
    }

    private void addPoint(TennisPlayer player) {
        match.getMatchScore().addPointTo(player);
    }

    private void addPoints(TennisPlayer player, int count) {
        for (int i = 0; i < count; i++) {
            addPoint(player);
        }
    }

    private void winGame(TennisPlayer player) {
        addPoints(player, 4);
    }

    private void winGames(TennisPlayer player, int count) {
        for (int i = 0; i < count; i++) {
            winGame(player);
        }
    }

    // ---- Simple getters ------------------------------------------------------------------

    @Test
    void getPlayers_returnPlayersFromConstructor() {
        assertSame(player1, match.getPlayer1());
        assertSame(player2, match.getPlayer2());
    }

    @Test
    void getMatchScore_isNotNullAndStable() {
        assertNotNull(match.getMatchScore());
        assertSame(match.getMatchScore(), match.getMatchScore());
    }

    // ---- Initial state -------------------------------------------------------------------

    @Test
    void newMatch_setScoreIsZeroForBoth() {
        assertEquals(0, match.getSetScoreFor(player1));
        assertEquals(0, match.getSetScoreFor(player2));
    }

    @Test
    void newMatch_gameScoreIsZeroForBoth() {
        assertEquals(0, match.getGameScoreFor(player1));
        assertEquals(0, match.getGameScoreFor(player2));
    }

    @Test
    void newMatch_pointScoreIsLoveForBoth() {
        assertEquals(PointScore.LOVE, match.getPointScoreFor(player1));
        assertEquals(PointScore.LOVE, match.getPointScoreFor(player2));
    }

    @Test
    void newMatch_tieBreakScoreIsNull() {
        assertNull(match.getTieBreakScoreFor(player1));
        assertNull(match.getTieBreakScoreFor(player2));
    }

    // ---- Scores after play (depend on the assumed addPoint helper) ------------------------

    @Test
    void pointScore_reflectsPointsInCurrentGame() {
        addPoint(player1);
        assertEquals(PointScore.FIFTEEN, match.getPointScoreFor(player1));
        assertEquals(PointScore.LOVE, match.getPointScoreFor(player2));

        addPoint(player1);
        assertEquals(PointScore.THIRTY, match.getPointScoreFor(player1));
    }

    @Test
    void winningGame_incrementsGameScoreAndResetsPointScore() {
        winGame(player1);

        assertEquals(1, match.getGameScoreFor(player1));
        assertEquals(0, match.getGameScoreFor(player2));
        assertEquals(PointScore.LOVE, match.getPointScoreFor(player1));
        assertEquals(PointScore.LOVE, match.getPointScoreFor(player2));
    }

    @Test
    void gameScores_trackBothPlayersIndependently() {
        winGames(player1, 3);
        winGames(player2, 2);

        assertEquals(3, match.getGameScoreFor(player1));
        assertEquals(2, match.getGameScoreFor(player2));
    }

    @Test
    void winningSet_incrementsSetScoreAndResetsGameScore() {
        winGames(player1, 6);

        assertEquals(1, match.getSetScoreFor(player1));
        assertEquals(0, match.getSetScoreFor(player2));
        assertEquals(0, match.getGameScoreFor(player1));
        assertEquals(0, match.getGameScoreFor(player2));
    }

    @Test
    void setScore_isNotAffectedByUnfinishedSet() {
        winGames(player1, 5);

        assertEquals(0, match.getSetScoreFor(player1));
        assertEquals(5, match.getGameScoreFor(player1));
    }

    @Nested
    class TieBreak {

        @BeforeEach
        void goToSixAll() {
            for (int i = 0; i < 5; i++) {
                winGame(player1);
                winGame(player2);
            }
            winGame(player1);
            winGame(player2);
        }

        @Test
        void tieBreakScore_isZeroZeroAtStart() {
            assertEquals(0, match.getTieBreakScoreFor(player1));
            assertEquals(0, match.getTieBreakScoreFor(player2));
        }

        @Test
        void pointScore_isNullDuringTieBreak() {
            assertNull(match.getPointScoreFor(player1));
            assertNull(match.getPointScoreFor(player2));
        }

        @Test
        void tieBreakScore_countsPoints() {
            addPoints(player1, 3);
            addPoints(player2, 2);

            assertEquals(3, match.getTieBreakScoreFor(player1));
            assertEquals(2, match.getTieBreakScoreFor(player2));
        }

        @Test
        void gameScore_staysSixAllDuringTieBreak() {
            assertEquals(6, match.getGameScoreFor(player1));
            assertEquals(6, match.getGameScoreFor(player2));
        }
    }

//    without @Nested

//    private void goToSixAll() {
//        for (int i = 0; i < 5; i++) {
//            winGame(alice);
//            winGame(bob);
//        }
//        winGame(alice);
//        winGame(bob);
//    }
//
//    @Test
//    void tieBreakScore_isZeroZeroAtStart() {
//        goToSixAll();
//
//        assertEquals(0, match.getTieBreakScoreFor(alice));
//        assertEquals(0, match.getTieBreakScoreFor(bob));
//    }
//
//    @Test
//    void pointScore_isNullDuringTieBreak() {
//        goToSixAll();
//
//        assertNull(match.getPointScoreFor(alice));
//        assertNull(match.getPointScoreFor(bob));
//    }
}
