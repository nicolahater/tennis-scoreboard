package dev.backend.domain;

import java.util.HashMap;
import java.util.Map;

public class TennisSet {
    private final TennisPlayer player1;
    private final TennisPlayer player2;

    private final Map<TennisPlayer, Integer> gameScore = new HashMap<>();
    private TennisGame currentGame;
    private final TennisTieBreak tieBreak;
    private TennisPlayer winner = null;

    public TennisSet(TennisPlayer player1, TennisPlayer player2) {
        this.player1 = player1;
        this.player2 = player2;

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
                winner = player;
            }
            return;
        }

        currentGame.addPointTo(player);
        if(currentGame.isFinished()) {
//            TennisPlayer currentGameWinner = currentGame.getWinner();
            gameScore.put(player, gameScore.get(player) + 1);
            currentGame = new TennisGame(player1, player2);
        }
        Integer playersGameScore = gameScore.get(player);

        TennisPlayer opponent = getOpponent(player);
        Integer opponentsGameScore = gameScore.get(opponent);

        //
        if((playersGameScore == 6 && opponentsGameScore < 5) || (playersGameScore == 7 && opponentsGameScore == 5)) {
            winner = player;
        }
    }

    public TennisTieBreak getTieBreak() {
        return tieBreak;
    }

    // TODO: I have the same methods in TennisGame, need to inherit from main class


    // TODO: maybe make for all class getCurren and put it in parent class
//    public TennisGame getCurren {
    public TennisGame getCurrentGame() {
        return currentGame;
    }

    public TennisPlayer getOpponent(TennisPlayer player) {
        return player.equals(player1) ? player2 : player1;
    }

    public TennisPlayer getWinner() {
        return winner;
    }

    public Integer getScore(TennisPlayer player) {
//        return isTieBreak() ? null : gameScore.get(player);
        return gameScore.get(player);
    }

    public boolean isFinished() {
        return winner != null;
    }

    public boolean isTieBreak() {
        return gameScore.get(player1) == 6 && gameScore.get(player2) == 6;
    }

    public Integer getTieBreakScore(TennisPlayer player) {
        return isTieBreak() ? tieBreak.getScore(player) : null;
    }


}
