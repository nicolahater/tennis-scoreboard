package dev.backend.service;

import dev.backend.dao.MatchDao;
import dev.backend.dao.PlayerDao;
import dev.backend.dto.CreateFinishedMatchRequestDTO;
import dev.backend.entity.MatchEntity;
import dev.backend.entity.PlayerEntity;
import org.springframework.stereotype.Service;

@Service
public class FinishedMatchService {
    private final PlayerDao playerDao;
    private final MatchDao matchDao;

    public FinishedMatchService(PlayerDao playerDao, MatchDao matchDao) {
        this.playerDao = playerDao;
        this.matchDao = matchDao;
    }

    // TODO: when I start writing tests, check if I have already one player in db
    // TODO: this should be transactional?
    public void create(CreateFinishedMatchRequestDTO requestDTO) {
//        PlayerEntity player1 = new PlayerEntity(requestDTO.firstPlayerName());
//        PlayerEntity player2 = new PlayerEntity(requestDTO.secondPlayerName());
//
//        // TODO: here may be exception inside if we already have this player, but it will work fine.
//        //       But, I guess, it is better to check if we have this player and only then to save it in db
//        //       Also, it can implemented like method save () in Spring Data JPA
//        //      firstly, it returns this entity
//        //      secondly, it checks if isNew(entity) and if not in executes merge()
//        //      And as I remember isNew is implemented something like "select 1 from Entity e where e.column_name = :column_value"
//



//        player1 = playerDao.save(player1);
//        player2 = playerDao.save(player2);
        //TODO: or, I think that variant from above is better, because in that case we can use mapper - we mapping all dto to entity and just save(or get existing entity) it to db
        PlayerEntity player1 = playerDao.create(requestDTO.firstPlayerName());
        PlayerEntity player2 = playerDao.create(requestDTO.secondPlayerName());


        PlayerEntity winner;
        if(requestDTO.winnerName().equals(requestDTO.firstPlayerName())) {
            winner = player1;
        } else if(requestDTO.winnerName().equals(requestDTO.secondPlayerName())) {
            winner = player2;
        } else {
            // TODO: because of this exception this whole method should be transactional
            throw new IllegalStateException(
                    "Unknown winner.The winner's name does not match the name of the players."
            );
        }

        MatchEntity match = new MatchEntity(player1, player2, winner);
        matchDao.save(match);
    }
}
