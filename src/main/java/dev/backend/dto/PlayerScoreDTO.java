package dev.backend.dto;

public record PlayerScoreDTO(
        String name,
        String points,
        Integer games,
        Integer sets,
        Integer tieBreakPoints
) {
}
