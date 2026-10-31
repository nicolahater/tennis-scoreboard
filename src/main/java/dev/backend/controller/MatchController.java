package dev.backend.controller;

import dev.backend.dto.*;
import dev.backend.service.FinishedMatchService;
import dev.backend.service.OngoingMatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

// TODO: in refractor/extract-shared-logic in Gameplay I can in method getScore return Object and in children I can check if it is "Integer" or "PointScore"
// TODO: in refractor/extract-shared-logic in Gameplay I can in method getScore return Object and in children I can check if it is "Integer" or "PointScore"
// TODO: in refractor/extract-shared-logic in Gameplay I can in method getScore return Object and in children I can check if it is "Integer" or "PointScore"
// TODO: in refractor/extract-shared-logic in Gameplay I can in method getScore return Object and in children I can check if it is "Integer" or "PointScore"
// TODO: in refractor/extract-shared-logic in Gameplay I can in method getScore return Object and in children I can check if it is "Integer" or "PointScore"
// TODO: in refractor/extract-shared-logic in Gameplay I can in method getScore return Object and in children I can check if it is "Integer" or "PointScore"

@RestController
@RequestMapping("/matches")
public class MatchController {
    private final OngoingMatchService ongoingMatchService;
    private final FinishedMatchService finishedMatchService;

    public MatchController(OngoingMatchService ongoingMatchService, FinishedMatchService finishedMatchService) {
        this.ongoingMatchService = ongoingMatchService;
        this.finishedMatchService = finishedMatchService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public CreateMatchResponseDTO createMatch(@RequestBody CreateMatchRequestDTO requestDTO) {
        return ongoingMatchService.create(requestDTO);
    }

    @GetMapping(path = "/{uuid}")
    public OngoingMatchDTO getOngoingMatch(@PathVariable("uuid") UUID uuid) {

        return ongoingMatchService.getOngoingMatch(uuid);
    }

    @GetMapping
    public FinishedMatchResponseDTO getFinishedMatch(
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "player_name", required = false) String playerName
//            @RequestParam(required = false) PaginationDTO pagination
    ) {
        return finishedMatchService.getAll(playerName, page);
//        return finishedMatchService.getAll(playerName, page, 2);
    }

    @PostMapping(path = "/{uuid}/point")
    public OngoingMatchDTO addPointToPlayer(
            @PathVariable("uuid") UUID uuid,
            @RequestBody AddPointRequestDTO requestDTO
    ) {
        return ongoingMatchService.addPointToPlayer(uuid, requestDTO);
    }
}
