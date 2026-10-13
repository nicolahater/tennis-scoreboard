package dev.backend.dto;

public record FinishedMatchDTO(
        String firstPlayerName,
        String secondPlayerName,
        String winnerName
) {
}
