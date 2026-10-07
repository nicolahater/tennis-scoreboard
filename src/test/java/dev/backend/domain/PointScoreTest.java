package dev.backend.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PointScoreTest {
    @Test
    void next_followsTennisOrder() {
        assertEquals(PointScore.FIFTEEN, PointScore.LOVE.next());
        assertEquals(PointScore.THIRTY, PointScore.FIFTEEN.next());
        assertEquals(PointScore.FORTY, PointScore.THIRTY.next());
        assertEquals(PointScore.ADVANTAGE, PointScore.FORTY.next());
        assertEquals(PointScore.GAME, PointScore.ADVANTAGE.next());
    }

    @Test
    void next_onGame_throws() {
        assertThrows(ArrayIndexOutOfBoundsException.class, PointScore.GAME::next);
//        assertThrows(ArrayIndexOutOfBoundsException.class, () -> PointScore.GAME.next());
    }
}
