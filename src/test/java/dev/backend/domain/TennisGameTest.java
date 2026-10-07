package dev.backend.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TennisGameTest {
    private TennisPlayer player1;
    private TennisPlayer player2;
    private TennisGame game;

    @BeforeEach
    void setUp() {
        player1 = new TennisPlayer("Alice");
        player2 = new TennisPlayer("Bob");
        game = new TennisGame(player1, player2);
    }

    private void addPoints(TennisPlayer player, int count) {
        for (int i = 0; i < count; i++) {
            game.addPointTo(player);
        }
    }

    private void deuce() {
        addPoints(player1, 3);
        addPoints(player2, 3);
    }

    @Test
    void newGame_startsAtLoveAllAndNotFinished() {
        assertEquals(PointScore.LOVE, game.getScore(player1));
        assertEquals(PointScore.LOVE, game.getScore(player2));
        assertFalse(game.isFinished());
        assertNull(game.getWinner());
    }

    @Test
    void addPoint_progressesThroughScores() {
        game.addPointTo(player1);
        assertEquals(PointScore.FIFTEEN, game.getScore(player1));
        game.addPointTo(player1);
        assertEquals(PointScore.THIRTY, game.getScore(player1));
        game.addPointTo(player1);
        assertEquals(PointScore.FORTY, game.getScore(player1));
        assertEquals(PointScore.LOVE, game.getScore(player2));
        assertFalse(game.isFinished());
    }

    @Test
    void playerWinsFromFortyLove() {
        addPoints(player1, 4);
        assertTrue(game.isFinished());
        assertEquals(player1, game.getWinner());
    }

    @Test
    void playerWinsFromFortyThirty() {
        addPoints(player2, 2);
        addPoints(player1, 4);
        assertTrue(game.isFinished());
        assertEquals(player1, game.getWinner());
    }

    @Test
    void secondPlayerCanWin() {
        addPoints(player2, 4);
        assertEquals(player2, game.getWinner());
    }

    @Test
    void deuce_isBothAtForty() {
        deuce();
        assertEquals(PointScore.FORTY, game.getScore(player1));
        assertEquals(PointScore.FORTY, game.getScore(player2));
        assertFalse(game.isFinished());
    }

    @Test
    void advantage_fromDeuceDoesNotEndGame() {
        deuce();
        game.addPointTo(player1);
        assertEquals(PointScore.ADVANTAGE, game.getScore(player1));
        assertEquals(PointScore.FORTY, game.getScore(player2));
        assertFalse(game.isFinished());
    }

    @Test
    void opponentScoringOnAdvantage_returnsToDeuce() {
        deuce();
        game.addPointTo(player1);
        game.addPointTo(player2);
        assertEquals(PointScore.FORTY, game.getScore(player1));
        assertEquals(PointScore.FORTY, game.getScore(player2));
        assertFalse(game.isFinished());
    }

    @Test
    void scoringOnAdvantage_winsGame() {
        deuce();
        game.addPointTo(player1);
        game.addPointTo(player1);
        assertTrue(game.isFinished());
        assertEquals(player1, game.getWinner());
    }

    @Test
    void longDeuceBattle_stillResolves() {
        deuce();
        for (int i = 0; i < 5; i++) {
            game.addPointTo(player1);
            game.addPointTo(player2);
        }
        assertFalse(game.isFinished());
        game.addPointTo(player2);
        game.addPointTo(player2);
        assertEquals(player2, game.getWinner());
    }

    @Test
    void addPoint_afterFinish_throws() {
        addPoints(player1, 4);
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> game.addPointTo(player2));
        assertEquals("The game is finished already.", ex.getMessage());
    }

    @Test
    void getOpponent_returnsTheOtherPlayer() {
        assertEquals(player2, game.getOpponent(player1));
        assertEquals(player1, game.getOpponent(player2));
    }
}
