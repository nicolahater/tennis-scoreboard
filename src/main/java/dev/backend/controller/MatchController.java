package dev.backend.controller;

import dev.backend.domain.TennisMatch;
import dev.backend.domain.TennisMatchScore;
import dev.backend.domain.TennisPlayer;
import dev.backend.dto.CreateMatchRequestDTO;
import dev.backend.dto.CreateMatchResponseDTO;
import dev.backend.dto.OngoingMatchDTO;
import dev.backend.mapper.OngoingMatchMapper;
import dev.backend.service.MatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/matches")
public class MatchController {
    private final MatchService matchService;
    private final OngoingMatchMapper ongoingMatchMapper;

    public MatchController(MatchService matchService, OngoingMatchMapper ongoingMatchMapper) {
        this.matchService = matchService;
        this.ongoingMatchMapper = ongoingMatchMapper;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public CreateMatchResponseDTO createMatch(@RequestBody CreateMatchRequestDTO requestDTO) {
        return matchService.create(requestDTO);
    }

    @GetMapping(path = "/{uuid}")
    public OngoingMatchDTO getOngoingMatch(@PathVariable("uuid") UUID uuid) {
        TennisMatch match = matchService.getOngoingMatch(uuid);
        TennisPlayer player1 = match.getPlayer1();
        TennisPlayer player2 = match.getPlayer2();

        return ongoingMatchMapper.toMatchMapperDTO(match);
    }
}
