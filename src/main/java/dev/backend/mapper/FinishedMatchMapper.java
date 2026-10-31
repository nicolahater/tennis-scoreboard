package dev.backend.mapper;

import dev.backend.domain.TennisMatch;
import dev.backend.dto.CreateFinishedMatchRequestDTO;
import dev.backend.dto.FinishedMatchDTO;
import dev.backend.entity.MatchEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FinishedMatchMapper {
    public CreateFinishedMatchRequestDTO toCreateFinishedMatchRequestDTO(TennisMatch match) {
        return new CreateFinishedMatchRequestDTO(
                match.getPlayer1().name(),
                match.getPlayer2().name(),
                match.getMatchScore().getWinner().name()
        );
    }

    // TODO: null checking

    public FinishedMatchDTO toFinishedMatchDTO(MatchEntity match) {
        return new FinishedMatchDTO(
                match.getPlayer1().getName(),
                match.getPlayer2().getName(),
                match.getWinner().getName()
        );
    }

    public List<FinishedMatchDTO> toFinishedMatchDTOs(List<MatchEntity> matches) {
        return matches.stream().map(this::toFinishedMatchDTO).toList();
    }
}
