package dev.backend.service;

import dev.backend.dao.MatchDao;
import dev.backend.dao.PlayerDao;
import dev.backend.domain.TennisMatchScore;
import dev.backend.domain.TennisPlayer;
import dev.backend.dto.CreateMatchRequestDTO;
import dev.backend.dto.CreateMatchResponseDTO;
import dev.backend.entity.PlayerEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MatchService {
    private final PlayerDao playerDao;
    private final MatchDao matchDao;
    private ConcurrentHashMap<UUID, TennisMatchScore> concurrentHashMap = new ConcurrentHashMap<>();


    public MatchService(PlayerDao playerDao, MatchDao matchDao) {
        this.playerDao = playerDao;
        this.matchDao = matchDao;
    }

    public CreateMatchResponseDTO create(CreateMatchRequestDTO requestDTO) {
        TennisPlayer player1 = new TennisPlayer(requestDTO.firstPlayerName());
        TennisPlayer player2 = new TennisPlayer(requestDTO.secondPlayerName());

        TennisMatchScore match = new TennisMatchScore(player1, player2);

        UUID matchUUID = UUID.randomUUID();
        concurrentHashMap.put(matchUUID, match);


        return new CreateMatchResponseDTO(matchUUID);
    }

    public TennisMatchScore getOngoingMatch(UUID uuid) {
        return concurrentHashMap.get(uuid);
    }
}
