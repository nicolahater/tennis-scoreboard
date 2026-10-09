package dev.backend.dto;

public record OngoingMatchDTO(
        PlayerScoreDTO firstPlayer,
        PlayerScoreDTO secondPlayer,
        String winnerName
) {
}
