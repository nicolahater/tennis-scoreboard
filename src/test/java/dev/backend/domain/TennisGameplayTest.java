package dev.backend.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TennisGameplayTest {
    private TennisPlayer player1;
    private TennisPlayer player2;
    private TennisPlayer stranger;
    private TennisGameplay gameplay;

    @BeforeEach
    void setUp() {
        player1 = new TennisPlayer("Player 1");
        player2 = new TennisPlayer("Player 2");
        stranger = new TennisPlayer("Unknown");
        gameplay = new TennisGameplay(player1, player2);
    }

    @Nested
    class Construction {

        @Test
        void exposesBothPlayers() {
            assertSame(player1, gameplay.getPlayer1());
            assertSame(player2, gameplay.getPlayer2());
        }

        @Test
        void newGameHasNoWinnerAndIsNotFinished() {
            assertNull(gameplay.getWinner());
            assertFalse(gameplay.isFinished());
        }
    }

    @Nested
    class GetOpponent {

        @Test
        void returnsPlayer2ForPlayer1() {
            assertSame(player2, gameplay.getOpponent(player1));
        }

        @Test
        void returnsPlayer1ForPlayer2() {
            assertSame(player1, gameplay.getOpponent(player2));
        }

        @Test
        void throwsNpeForNullPlayer() {
            NullPointerException ex = assertThrows(NullPointerException.class,
                    () -> gameplay.getOpponent(null));
            assertEquals("Player must not be null", ex.getMessage());
        }

        @Test
        void throwsIllegalArgumentForUnknownPlayer() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> gameplay.getOpponent(stranger));
            assertEquals("Unknown player", ex.getMessage());
        }
    }

    @Nested
    class SetWinner {

        @Test
        void setsPlayer1AsWinner() {
            gameplay.setWinner(player1);

            assertSame(player1, gameplay.getWinner());
            assertTrue(gameplay.isFinished());
        }

        @Test
        void setsPlayer2AsWinner() {
            gameplay.setWinner(player2);

            assertSame(player2, gameplay.getWinner());
            assertTrue(gameplay.isFinished());
        }

        @Test
        void throwsNpeForNullWinner() {
            assertThrows(NullPointerException.class, () -> gameplay.setWinner(null));
            assertFalse(gameplay.isFinished());
        }

        @Test
        void throwsIllegalArgumentForUnknownWinner() {
            assertThrows(IllegalArgumentException.class, () -> gameplay.setWinner(stranger));
            assertNull(gameplay.getWinner());
            assertFalse(gameplay.isFinished());
        }

        @Test
        void throwsIfGameAlreadyFinished() {
            gameplay.setWinner(player1);

            IllegalStateException ex = assertThrows(IllegalStateException.class,
                    () -> gameplay.setWinner(player2));
            assertEquals("Game is already finished", ex.getMessage());
        }

        @Test
        void winnerIsUnchangedAfterRejectedSecondSet() {
            gameplay.setWinner(player1);

            assertThrows(IllegalStateException.class, () -> gameplay.setWinner(player2));

            assertSame(player1, gameplay.getWinner());
        }

        @Test
        void settingSameWinnerTwiceAlsoThrows() {
            gameplay.setWinner(player1);

            assertThrows(IllegalStateException.class, () -> gameplay.setWinner(player1));
        }

        @Test
        void validatesPlayerBeforeCheckingFinishedState() {
            gameplay.setWinner(player1);

            // Unknown player is rejected with IllegalArgumentException,
            // not IllegalStateException, because requireKnownPlayer runs first.
            assertThrows(IllegalArgumentException.class, () -> gameplay.setWinner(stranger));
        }
    }
}