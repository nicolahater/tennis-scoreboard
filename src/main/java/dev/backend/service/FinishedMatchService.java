package dev.backend.service;

import dev.backend.dao.MatchDao;
import dev.backend.dao.PlayerDao;
import dev.backend.dto.CreateFinishedMatchRequestDTO;
import dev.backend.dto.FinishedMatchDTO;
import dev.backend.dto.FinishedMatchResponseDTO;
import dev.backend.entity.MatchEntity;
import dev.backend.entity.PlayerEntity;
import dev.backend.mapper.FinishedMatchMapper;
import dev.backend.utils.MyPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinishedMatchService {
    private final PlayerDao playerDao;
    private final MatchDao matchDao;
    private final FinishedMatchMapper matchMapper;
    private static final Integer DEFAULT_SIZE = 5;

    public FinishedMatchService(PlayerDao playerDao, MatchDao matchDao, FinishedMatchMapper matchMapper) {
        this.playerDao = playerDao;
        this.matchDao = matchDao;
        this.matchMapper = matchMapper;
    }

    public FinishedMatchResponseDTO getAll(String playerName, Integer page) {
        return getAll(playerName, page, DEFAULT_SIZE);
    }

    public FinishedMatchResponseDTO getAll(String playerName, Integer page, Integer size) {
//        int offset = (page-1) * size;
        MyPage<MatchEntity> pageMatches = matchDao.getAll(playerName, page, size);

        List<FinishedMatchDTO> matchDTOs = matchMapper.toFinishedMatchDTOs(pageMatches.getContent());

        return new FinishedMatchResponseDTO(
                matchDTOs,
                pageMatches.getCurrentPage(),
                pageMatches.getTotalPages()
        );
    }

//  TODO when this will be Transactional I will be able to write it like this and in save to make merge() if entity is null, and everything will be in minimum requests to db. This works because everything is in one transaction
//  TODO: when I start writing tests, check if I have already one player in db
    public void create(CreateFinishedMatchRequestDTO requestDTO) {
        PlayerEntity player1 = playerDao.findByName(requestDTO.firstPlayerName())
                .orElse(new PlayerEntity(requestDTO.firstPlayerName()));
        PlayerEntity player2 = playerDao.findByName(requestDTO.secondPlayerName())
                .orElse(new PlayerEntity(requestDTO.secondPlayerName()));

        player1 = playerDao.save(player1);
        player2 = playerDao.save(player2);

        PlayerEntity winner = getWinner(player1, player2, requestDTO.winnerName());

        MatchEntity match = new MatchEntity(player1, player2, winner);
        matchDao.save(match);
    }

    private PlayerEntity getWinner(PlayerEntity player1, PlayerEntity player2, String winnerName) {
        if(winnerName.equals(player1.getName())) {
            return player1;
        } else if(winnerName.equals(player2.getName())) {
            return player2;
        }
        throw new IllegalStateException(
                "Unknown winner.The winner's name does not match the name of the players."
        );
    }
}
