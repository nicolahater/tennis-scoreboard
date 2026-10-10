package dev.backend.controller;

import dev.backend.dto.AddPointRequestDTO;
import dev.backend.dto.CreateMatchRequestDTO;
import dev.backend.dto.CreateMatchResponseDTO;
import dev.backend.dto.OngoingMatchDTO;
import dev.backend.service.MatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/matches")
public class MatchController {
    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public CreateMatchResponseDTO createMatch(@RequestBody CreateMatchRequestDTO requestDTO) {
        return matchService.create(requestDTO);
    }

    @GetMapping(path = "/{uuid}")
    public OngoingMatchDTO getOngoingMatch(@PathVariable("uuid") UUID uuid) {

        return matchService.getOngoingMatch(uuid);
    }

    @PostMapping(path = "/{uuid}/point")
    public OngoingMatchDTO addPointToPlayer(
            @PathVariable("uuid") UUID uuid,
            @RequestBody AddPointRequestDTO requestDTO
    ) {
        return matchService.addPointToPlayer(uuid, requestDTO);
    }
}
