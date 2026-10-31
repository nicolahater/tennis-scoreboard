package dev.backend.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TennisMatchScoreTest {

    private TennisPlayer player1;
    private TennisPlayer player2;
    private TennisMatchScore matchScore;

    @BeforeEach
    void setUp() {
        player1 = new TennisPlayer("Player 1");
        player2 = new TennisPlayer("Player 2");
        matchScore = new TennisMatchScore(player1, player2);
    }

    /** Wins a whole set 6-0 (4 points in each set). Avoids tie-break and 7-5 paths. */
    private void winSet(TennisPlayer player) {
        for (int i = 0; i < 6*4; i++) {
            matchScore.addPointTo(player);
        }
    }

    @Test
    void newMatch_isNotFinishedAndHasNoWinner() {
        assertFalse(matchScore.isFinished());
        assertNull(matchScore.getWinner());
    }

    @Test
    void pointsInsideASet_doNotFinishMatch() {
        for (int i = 0; i < 10; i++) {
            matchScore.addPointTo(player1);
        }
        assertFalse(matchScore.isFinished());
        assertNull(matchScore.getWinner());
    }

    @Test
    void winningOneSet_doesNotFinishMatch() {
        winSet(player1);
        assertFalse(matchScore.isFinished());
        assertNull(matchScore.getWinner());
    }

    @Test
    void winningTwoSetsInARow_finishesMatchWithThatWinner() {
        winSet(player1);
        winSet(player1);
        assertTrue(matchScore.isFinished());
        assertEquals(player1, matchScore.getWinner());
    }

    @Test
    void secondPlayerCanWinMatch() {
        winSet(player2);
        winSet(player2);
        assertTrue(matchScore.isFinished());
        assertEquals(player2, matchScore.getWinner());
    }

    @Test
    void oneSetEach_doesNotFinishMatch() {
        winSet(player1);
        winSet(player2);
        assertFalse(matchScore.isFinished());
        assertNull(matchScore.getWinner());
    }

    @Test
    void thirdSetDecidesMatch_winnerIsWhoeverWinsIt() {
        winSet(player1);
        winSet(player2);
        winSet(player2);
        assertTrue(matchScore.isFinished());
        assertEquals(player2, matchScore.getWinner());
    }

    @Test
    void comebackFromOneSetDown_aliceWins() {
        winSet(player2);
        winSet(player1);
        winSet(player1);
        assertEquals(player1, matchScore.getWinner());
    }

    @Test
    void addPoint_afterMatchFinished_throwsAndKeepsWinner() {
        winSet(player1);
        winSet(player1);
        assertThrows(IllegalStateException.class, () -> matchScore.addPointTo(player2));
        assertEquals(player1, matchScore.getWinner());
    }
}
