package dev.backend.domain;

import java.util.HashMap;
import java.util.Map;

public class TennisGameplay {
    private final TennisPlayer player1;
    private final TennisPlayer player2;
    private final Map<TennisPlayer, Integer> gameScore = new HashMap<>();


    public TennisGameplay(TennisPlayer player1, TennisPlayer player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    private TennisPlayer winner = null;


    public TennisPlayer getOpponent(TennisPlayer player) {
//        return player.name().equals(player1.name()) ? player2 : player1;
//        // TODO: add this method and add test for it
//        if (!player.equals(player1) && !player.equals(player2)) throw new IllegalArgumentException("Unknown player");

        return player.equals(player1) ? player2 : player1;
    }

    public TennisPlayer getWinner() {
        return winner;
    }


//    public PointScore getScore(TennisPlayer player) {
//        return scores.get(player);
//    }

    public boolean isFinished() {
        return winner != null;
    }
}
