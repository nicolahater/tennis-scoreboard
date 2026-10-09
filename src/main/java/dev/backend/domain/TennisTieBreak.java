package dev.backend.domain;

import java.util.HashMap;
import java.util.Map;

public class TennisTieBreak extends TennisGameplay {
    private final Map<TennisPlayer, Integer> tieBreakScore = new HashMap<>();

    public TennisTieBreak(TennisPlayer player1, TennisPlayer player2) {
        super(player1, player2);
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
            setWinner(player);
        }
    }

    public Integer getScore(TennisPlayer player) {
        return tieBreakScore.get(player);
    }
}
