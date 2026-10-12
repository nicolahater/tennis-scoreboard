package dev.backend.service;

import dev.backend.dao.MatchDao;
import dev.backend.dao.PlayerDao;
import dev.backend.domain.TennisMatch;
import dev.backend.domain.TennisPlayer;
import dev.backend.dto.AddPointRequestDTO;
import dev.backend.dto.CreateMatchRequestDTO;
import dev.backend.dto.CreateMatchResponseDTO;
import dev.backend.dto.OngoingMatchDTO;
import dev.backend.mapper.OngoingMatchMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OngoingMatchService {
    private final PlayerDao playerDao;
    private final MatchDao matchDao;
    private final OngoingMatchMapper matchMapper;
    private final FinishedMatchService finishedMatchService;
    private ConcurrentHashMap<UUID, TennisMatch> concurrentHashMap = new ConcurrentHashMap<>();

    public OngoingMatchService(PlayerDao playerDao, MatchDao matchDao, OngoingMatchMapper matchMapper, FinishedMatchService finishedMatchService) {
        this.playerDao = playerDao;
        this.matchDao = matchDao;
        this.matchMapper = matchMapper;
        this.finishedMatchService = finishedMatchService;
    }

    public CreateMatchResponseDTO create(CreateMatchRequestDTO requestDTO) {
        // TODO: this should be in mapper?
        TennisPlayer player1 = new TennisPlayer(requestDTO.firstPlayerName());
        TennisPlayer player2 = new TennisPlayer(requestDTO.secondPlayerName());

        TennisMatch match = new TennisMatch(player1, player2);

        UUID matchUUID = UUID.randomUUID();
        concurrentHashMap.put(matchUUID, match);


        return new CreateMatchResponseDTO(matchUUID); // TODO: in mapper
    }

    public OngoingMatchDTO getOngoingMatch(UUID uuid) {
        return matchMapper.toMatchMapperDTO(concurrentHashMap.get(uuid));
    }

    // TODO: is this should be transactional? I guess yes
    public OngoingMatchDTO addPointToPlayer(UUID uuid, AddPointRequestDTO requestDTO) {
        TennisMatch match = concurrentHashMap.get(uuid);
        match.getMatchScore().addPointTo(new TennisPlayer(requestDTO.name())); // TODO: in score maps instead of TennisPlayer use just String, also you can avoid using TennisPlayer or add createPlayer method and create player at first

        if(match.getMatchScore().isFinished()) {
            finishedMatchService.create(matchMapper.toCreateFinishedMatchRequestDTO(match));
        }

        return matchMapper.toMatchMapperDTO(match);
    }
}
