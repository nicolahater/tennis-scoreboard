package dev.backend.service;

import dev.backend.dao.MatchDao;
import dev.backend.dao.PlayerDao;
import dev.backend.domain.TennisMatch;
import dev.backend.domain.TennisMatchScore;
import dev.backend.domain.TennisPlayer;
import dev.backend.dto.CreateMatchRequestDTO;
import dev.backend.dto.CreateMatchResponseDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MatchService {
    private final PlayerDao playerDao;
    private final MatchDao matchDao;
    private ConcurrentHashMap<UUID, TennisMatch> concurrentHashMap = new ConcurrentHashMap<>();


    public MatchService(PlayerDao playerDao, MatchDao matchDao) {
        this.playerDao = playerDao;
        this.matchDao = matchDao;
    }

    public CreateMatchResponseDTO create(CreateMatchRequestDTO requestDTO) {
        TennisPlayer player1 = new TennisPlayer(requestDTO.firstPlayerName());
        TennisPlayer player2 = new TennisPlayer(requestDTO.secondPlayerName());

        TennisMatch match = new TennisMatch(player1, player2);

        UUID matchUUID = UUID.randomUUID();
        concurrentHashMap.put(matchUUID, match);


        return new CreateMatchResponseDTO(matchUUID);
    }

    public TennisMatch getOngoingMatch(UUID uuid) {
        return concurrentHashMap.get(uuid);
    }
}
