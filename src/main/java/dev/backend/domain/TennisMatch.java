package dev.backend.domain;

public class TennisMatch {
    private final TennisMatchScore matchScore;
    private final TennisPlayer player1;
    private final TennisPlayer player2;

    public TennisMatch(TennisPlayer player1, TennisPlayer player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.matchScore = new TennisMatchScore(player1, player2);
    }

    public TennisMatchScore getMatchScore() {
        return matchScore;
    }

    public TennisPlayer getPlayer1() {
        return player1;
    }

    public TennisPlayer getPlayer2() {
        return player2;
    }

    public Integer getSetScoreFor(TennisPlayer player) {
        return matchScore
                .getScoreFor(player);
    }

    public Integer getTieBreakScoreFor(TennisPlayer player) {
        return matchScore.getCurrentSet().isTieBreak()
                ? matchScore.getCurrentSet().getTieBreakScore(player)
                : null;
    }

    public Integer getGameScoreFor(TennisPlayer player) {
        return matchScore
                .getCurrentSet()
                .getScore(player);
    }

    public PointScore getPointScoreFor(TennisPlayer player) {
        return matchScore.getCurrentSet().isTieBreak()
                ? null
                : matchScore
                    .getCurrentSet()
                    .getCurrentGame()
                    .getScore(player);
    }


}
