package dev.backend.dto;

public record CreateMatchRequestDTO(
        String firstPlayerName,
        String secondPlayerName
) {
}
