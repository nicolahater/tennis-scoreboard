package dev.backend.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TennisTieBreakTest {
    private TennisPlayer player1;
    private TennisPlayer player2;
    private TennisTieBreak tieBreak;

    @BeforeEach
    void setUp() {
        player1 = new TennisPlayer("Player 1");
        player2 = new TennisPlayer("Player 2");
        tieBreak = new TennisTieBreak(player1, player2);
    }

    private void addPoints(TennisPlayer player, int count) {
        for (int i = 0; i < count; i++) {
            tieBreak.addPointTo(player);
        }
    }

    @Test
    void newTieBreak_isNotFinished() {
        assertFalse(tieBreak.isFinished());
    }

    @Test
    void firstPointCanBeAdded() {
        assertDoesNotThrow(() -> tieBreak.addPointTo(player1));
        assertFalse(tieBreak.isFinished());
    }

    @Test
    void sevenToZero_finishes() {
        addPoints(player1, 7);
        assertTrue(tieBreak.isFinished());
    }

    @Test
    void sixToZero_notFinished() {
        addPoints(player1, 6);
        assertFalse(tieBreak.isFinished());
    }

    @Test
    void sevenToFive_finishes() {
        for (int i = 0; i < 5; i++) {
            tieBreak.addPointTo(player1);
            tieBreak.addPointTo(player2);
        }
        addPoints(player1, 2);
        assertTrue(tieBreak.isFinished());
    }

    @Test
    void sevenToSix_doesNotFinish() {
        for (int i = 0; i < 6; i++) {
            tieBreak.addPointTo(player1);
            tieBreak.addPointTo(player2);
        }
        tieBreak.addPointTo(player1);
        assertFalse(tieBreak.isFinished());
    }

    @Test
    void mustWinByTwo_eightToSix() {
        for (int i = 0; i < 6; i++) {
            tieBreak.addPointTo(player1);
            tieBreak.addPointTo(player2);
        }
        tieBreak.addPointTo(player1);
        tieBreak.addPointTo(player1);
        assertTrue(tieBreak.isFinished());
    }

    @Test
    void secondPlayerCanWin() {
        addPoints(player2, 7);
        assertTrue(tieBreak.isFinished());
    }

    @Test
    void addPoint_afterFinish_throws() {
        addPoints(player1, 7);
        IllegalStateException ex =
                assertThrows(IllegalStateException.class, () -> tieBreak.addPointTo(player2));
        assertEquals("The tie-break is finished already.", ex.getMessage());
    }
}
