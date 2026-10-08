package dev.backend.domain;

import java.util.HashMap;
import java.util.Map;

public class TennisMatchScore {
    private final TennisPlayer player1;
    private final TennisPlayer player2;

    private final Map<TennisPlayer, Integer> setScore = new HashMap<>();
    private TennisSet currentSet;
    private TennisPlayer winner = null;

    private final static Integer SETS_NUMBER = 3;
    private final static Integer SETS_NUMBER_TO_WIN = SETS_NUMBER/2 + 1;

    public TennisMatchScore(TennisPlayer player1, TennisPlayer player2) {
        this.player1 = player1;
        this.player2 = player2;

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
            currentSet = new TennisSet(player1, player2);
        }

        Integer playersSetScore = setScore.get(player);
        if(playersSetScore.equals(SETS_NUMBER_TO_WIN)) {
            winner = player;
        }
    }


    public TennisPlayer getOpponent(TennisPlayer player) {
        return player.equals(player1) ? player2 : player1;
    }

    public TennisPlayer getWinner() {
        return winner;
    }

    public Integer getScore(TennisPlayer player) {
        return setScore.get(player);
    }

    public boolean isFinished() {
        return winner != null;
    }


    public TennisSet getCurrentSet() {
        return currentSet;
    }


    public Integer getSetScoreFor(TennisPlayer player) {
        return setScore.get(player);
    }

    public Integer getGameScoreFor(TennisPlayer player) {
        return currentSet.getScore(player);
    }

    public PointScore getPointScoreFor(TennisPlayer player) {
        return currentSet.getCurrentGame().getScore(player);
    }

}
