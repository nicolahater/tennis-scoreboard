package dev.backend.mapper;

import dev.backend.domain.TennisMatch;
import dev.backend.domain.TennisMatchScore;
import dev.backend.domain.TennisPlayer;
import dev.backend.domain.TennisPlayerScore;
import dev.backend.dto.OngoingMatchDTO;
import dev.backend.dto.PlayerScoreDTO;
import org.springframework.stereotype.Component;

@Component
public class OngoingMatchMapper {
    public OngoingMatchDTO toMatchMapperDTO(TennisMatch match) {
        TennisPlayer player1 = match.getPlayer1();
        TennisPlayer player2 = match.getPlayer2();


        PlayerScoreDTO player1DTO = new PlayerScoreDTO(
                player1.name(),
                String.valueOf(match.getPointScoreFor(player1)),
                match.getGameScoreFor(player1),
                match.getSetScoreFor(player1),
                match.getTieBreakScoreFor(player1)
        );

        PlayerScoreDTO player2DTO = new PlayerScoreDTO(
                player2.name(),
                String.valueOf(match.getPointScoreFor(player2)),
                match.getGameScoreFor(player2),
                match.getSetScoreFor(player2),
                match.getTieBreakScoreFor(player2)
        );

        String winnerName = match.getMatchScore().getWinner() != null
                            ? match.getMatchScore().getWinner().name()
                            : null;

        return new OngoingMatchDTO(player1DTO, player2DTO, winnerName);
    }
}
