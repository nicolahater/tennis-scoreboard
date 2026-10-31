package dev.backend.domain;

import java.util.HashMap;
import java.util.Map;

public class TennisSet extends TennisGameplay {
    private final Map<TennisPlayer, Integer> gameScore = new HashMap<>();
    private TennisGame currentGame;
    private final TennisTieBreak tieBreak;

    public TennisSet(TennisPlayer player1, TennisPlayer player2) {
        super(player1, player2);

        gameScore.put(player1, 0);
        gameScore.put(player2, 0);

        currentGame = new TennisGame(player1, player2);
        tieBreak = new TennisTieBreak(player1, player2);
    }

    public void addPointTo(TennisPlayer player) {
        if(isFinished()) {
            throw new IllegalStateException("The set is finished already.");
        }

        if (isTieBreak()) {
            tieBreak.addPointTo(player);
            if(tieBreak.isFinished()) {
                setWinner(player);
            }
            return;
        }

        currentGame.addPointTo(player);
        if(currentGame.isFinished()) {
            gameScore.put(player, gameScore.get(player) + 1);
            currentGame = new TennisGame(getPlayer1(), getPlayer2());
        }
        Integer playersGameScore = gameScore.get(player);

        TennisPlayer opponent = getOpponent(player);
        Integer opponentsGameScore = gameScore.get(opponent);

        //
        if((playersGameScore == 6 && opponentsGameScore < 5) || (playersGameScore == 7 && opponentsGameScore == 5)) {
            setWinner(player);
        }
    }

    public TennisTieBreak getTieBreak() {
        return tieBreak;
    }

    // TODO: maybe make for all class getCurren and put it in parent class
//    public TennisGame getCurren {
    public TennisGame getCurrentGame() {
        return currentGame;
    }

    public Integer getScore(TennisPlayer player) {
//        return isTieBreak() ? null : gameScore.get(player);
        return gameScore.get(player);
    }

    public boolean isTieBreak() {
        return gameScore.get(getPlayer1()) == 6 && gameScore.get(getPlayer2()) == 6;
    }

    public Integer getTieBreakScore(TennisPlayer player) {
        return isTieBreak() ? tieBreak.getScore(player) : null;
    }


}
