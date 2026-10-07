package dev.backend.domain;

import java.util.HashMap;
import java.util.Map;

public class TennisTieBreak {
    private final TennisPlayer player1;
    private final TennisPlayer player2;

    private final Map<TennisPlayer, Integer> tieBreakScore = new HashMap<>();
    private TennisPlayer winner = null;

    public TennisTieBreak(TennisPlayer player1, TennisPlayer player2) {
        this.player1 = player1;
        this.player2 = player2;
        tieBreakScore.put(player1, 0);
        tieBreakScore.put(player2, 0);
    }

    public void addPointTo(TennisPlayer player) {
        if(isFinished()) {
            throw new IllegalStateException("The tie-break is finished already.");
        }

        Integer playersNewScore = tieBreakScore.get(player) + 1;
        tieBreakScore.put(player, playersNewScore);

        TennisPlayer opponent = getOpponent(player);
        Integer opponentsScore = tieBreakScore.get(opponent);

        if(playersNewScore >= 7 && playersNewScore-opponentsScore >= 2) {
            winner = player;
        }
    }

    public TennisPlayer getOpponent(TennisPlayer player) {
        return player.equals(player1) ? player2 : player1;
    }

    public Integer getScore(TennisPlayer player) {
        return tieBreakScore.get(player);
    }

    public boolean isFinished() {
        return winner != null;
    }
}
