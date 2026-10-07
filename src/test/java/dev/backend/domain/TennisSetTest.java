package dev.backend.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TennisSetTest {
    private TennisPlayer player1;
    private TennisPlayer player2;
    private TennisSet set;

    @BeforeEach
    void setUp() {
        player1 = new TennisPlayer("Alice");
        player2 = new TennisPlayer("Bob");
        set = new TennisSet(player1, player2);
    }

    /** Wins a full game for the given player (4 straight points from 0-0). */
    private void winGame(TennisPlayer player) {
        for (int i = 0; i < 4; i++) {
            set.addPointTo(player);
        }
    }

    private void winGames(TennisPlayer player, int count) {
        for (int i = 0; i < count; i++) {
            winGame(player);
        }
    }

    /** Brings the set to 5-5 by alternating game wins. */
    private void goToFiveAll() {
        for (int i = 0; i < 5; i++) {
            winGame(player1);
            winGame(player2);
        }
    }

    @Test
    void newSet_isNotFinishedAndNotTieBreak() {
        assertFalse(set.isFinished());
        assertFalse(set.isTieBreak());
        assertNull(set.getWinner());
    }

    @Test
    void getOpponent_returnsTheOtherPlayer() {
        assertEquals(player2, set.getOpponent(player1));
        assertEquals(player1, set.getOpponent(player2));
    }

    @Test
    void sixLove_winsSet() {
        winGames(player1, 6);
        assertTrue(set.isFinished());
        assertEquals(player1, set.getWinner());
    }

    @Test
    void sixFour_winsSet() {
        winGames(player2, 4);
        winGames(player1, 6);
        assertTrue(set.isFinished());
        assertEquals(player1, set.getWinner());
    }

    @Test
    void fiveLove_doesNotWinSet() {
        winGames(player1, 5);
        assertFalse(set.isFinished());
    }

    @Test
    void sixFive_doesNotWinSet() {
        goToFiveAll();
        winGame(player1);
        assertFalse(set.isFinished());
        assertFalse(set.isTieBreak());
    }

    @Test
    void sevenFive_winsSet() {
        goToFiveAll();
        winGame(player1);
        winGame(player1);
        assertTrue(set.isFinished());
        assertEquals(player1, set.getWinner());
    }

    @Test
    void sixAll_startsTieBreak() {
        goToFiveAll();
        winGame(player1);
        winGame(player2);
        assertTrue(set.isTieBreak());
        assertFalse(set.isFinished());
    }

    @Test
    void tieBreak_winnerTakesTheSet() {
        goToFiveAll();
        winGame(player1);
        winGame(player2);

        for (int i = 0; i < 7; i++) {
            set.addPointTo(player2);
        }

        assertTrue(set.getTieBreak().isFinished());
        assertTrue(set.isFinished());
        assertEquals(player2, set.getWinner());
    }

    @Test
    void addPoint_afterSetFinished_throws() {
        winGames(player1, 6);
        IllegalStateException ex =
                assertThrows(IllegalStateException.class, () -> set.addPointTo(player2));
        assertEquals("The set is finished already.", ex.getMessage());
    }
}
