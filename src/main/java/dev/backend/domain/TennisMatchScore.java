package dev.backend.domain;

import java.util.HashMap;
import java.util.Map;

public class TennisMatchScore extends TennisGameplay {
    private final Map<TennisPlayer, Integer> setScore = new HashMap<>();
    private TennisSet currentSet;

    private final static Integer SETS_NUMBER = 3;
    private final static Integer SETS_NUMBER_TO_WIN = SETS_NUMBER/2 + 1;

    public TennisMatchScore(TennisPlayer player1, TennisPlayer player2) {
        super(player1, player2);

        setScore.put(player1, 0);
        setScore.put(player2, 0);

        currentSet = new TennisSet(player1, player2);
    }

    public void addPointTo(TennisPlayer player) {
        if(isFinished()) {
            throw new IllegalStateException("The match is finished already.");
        }

        currentSet.addPointTo(player);
        if(currentSet.isFinished()) {
            setScore.put(player, setScore.get(player) + 1);
            currentSet = new TennisSet(getPlayer1(), getPlayer2());
        }

        Integer playersSetScore = setScore.get(player);
        if(playersSetScore.equals(SETS_NUMBER_TO_WIN)) {
            setWinner(player);
        }
    }

    public Integer getScoreFor(TennisPlayer player) {
        return setScore.get(player);
    }

    public TennisSet getCurrentSet() {
        return currentSet;
    }
}
