package dev.backend.domain;

import java.util.HashMap;

public class TennisGameplay {
    private final TennisPlayer player1;
    private final TennisPlayer player2;
    private TennisPlayer winner = null;
//    private final Map<TennisPlayer, T> gameScore = new HashMap<>();


    public TennisGameplay(TennisPlayer player1, TennisPlayer player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public TennisPlayer getOpponent(TennisPlayer player) {
//        return player.name().equals(player1.name()) ? player2 : player1;
//        // TODO: add this method and add test for it
//        if (!player.equals(player1) && !player.equals(player2)) throw new IllegalArgumentException("Unknown player");

        return player.equals(player1) ? player2 : player1;
    }

    public TennisPlayer getWinner() {
        return winner;
    }

    public void setWinner(TennisPlayer winner) {
        this.winner = winner;
    }

//    public PointScore getScore(TennisPlayer player) {
//        return gameScore.get(player);
//    }


    public TennisPlayer getPlayer1() {
        return player1;
    }

    public TennisPlayer getPlayer2() {
        return player2;
    }

    public boolean isFinished() {
        return winner != null;
    }
}
