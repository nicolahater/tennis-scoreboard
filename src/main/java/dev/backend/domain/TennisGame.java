package dev.backend.domain;

import java.util.HashMap;
import java.util.Map;

public class TennisGame extends TennisGameplay {
    private final Map<TennisPlayer, PointScore> scores = new HashMap<>();
    private TennisPlayer winner = null;

    public TennisGame(TennisPlayer player1, TennisPlayer player2) {
        super(player1, player2);

        scores.put(player1, PointScore.LOVE);
        scores.put(player2, PointScore.LOVE);
    }

    public void addPointTo(TennisPlayer player) {
        if(isFinished()) {
            throw new IllegalStateException("The game is finished already.");
        }

        PointScore score = scores.get(player);
        PointScore nextPoint = score.next();
        scores.put(player, nextPoint);

        TennisPlayer opponent = getOpponent(player);
        PointScore opponentScore = scores.get(opponent);

        if(nextPoint.equals(PointScore.GAME)
                || nextPoint.equals(PointScore.ADVANTAGE)
                && opponentScore.ordinal() < PointScore.FORTY.ordinal()) {
            winner = player;


//            scores.put(player1, null);
//            scores.put(player2, null);
        }else if(nextPoint.equals(PointScore.ADVANTAGE) && opponentScore.equals(PointScore.ADVANTAGE)) {
            scores.put(player, PointScore.FORTY);
            scores.put(opponent, PointScore.FORTY);
        }
    }

    public PointScore getScore(TennisPlayer player) {
        return scores.get(player);
    }
}
