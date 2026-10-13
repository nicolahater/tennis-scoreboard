package dev.backend.dto;

import java.util.List;

public record FinishedMatchResponseDTO(
        List<FinishedMatchDTO> matches,
        Integer currentPage,
        Long totalPages
) {
}
