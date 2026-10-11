package dev.backend.dto;

public record CreateFinishedMatchRequestDTO(
    String firstPlayerName,
    String secondPlayerName,
    String winnerName
) {
}
