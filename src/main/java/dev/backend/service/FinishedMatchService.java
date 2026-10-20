package dev.backend.service;

import dev.backend.dao.MatchDao;
import dev.backend.dao.PlayerDao;
import dev.backend.dto.CreateFinishedMatchRequestDTO;
import dev.backend.dto.FinishedMatchDTO;
import dev.backend.dto.FinishedMatchResponseDTO;
import dev.backend.entity.MatchEntity;
import dev.backend.entity.PlayerEntity;
import dev.backend.exception.SamePlayersException;
import dev.backend.mapper.FinishedMatchMapper;
import dev.backend.utils.MyPage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinishedMatchService {
    private final PlayerDao playerDao;
    private final MatchDao matchDao;
    private final FinishedMatchMapper matchMapper;
    private final EntityManagerFactory emf;
    private static final Logger log = LoggerFactory.getLogger(FinishedMatchService.class);
    private static final Integer DEFAULT_SIZE = 5;

    public FinishedMatchService(PlayerDao playerDao, MatchDao matchDao, FinishedMatchMapper matchMapper, EntityManagerFactory emf) {
        this.playerDao = playerDao;
        this.matchDao = matchDao;
        this.matchMapper = matchMapper;
        this.emf = emf;
    }

    public FinishedMatchResponseDTO getAll(String playerName, Integer page) {
        return getAll(playerName, page, DEFAULT_SIZE);
    }

    public FinishedMatchResponseDTO getAll(String playerName, Integer page, Integer size) {
//        int offset = (page-1) * size;

        log.info("Getting matches....; page={}, playerName={}", page, playerName);

        EntityManager em = emf.createEntityManager();
        MyPage<MatchEntity> pageMatches;
        try {
            em.getTransaction().begin();
            pageMatches = matchDao.getAll(em, playerName, page, size);
            em.getTransaction().commit();
        } catch (Exception e) {
            log.info("Caught exception: {}; executing rollback....", e.getMessage());
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException(e);
        } finally {
            em.close();
        }

        log.info("Got matches....; page={}, playerName={}", page, playerName);

        List<FinishedMatchDTO> matchDTOs = matchMapper.toFinishedMatchDTOs(pageMatches.getContent());

        return new FinishedMatchResponseDTO(
                matchDTOs,
                pageMatches.getCurrentPage(),
                pageMatches.getTotalPages()
        );
    }

//  TODO when this will be Transactional I will be able to write it like this and in save to make merge() if entity is null, and everything will be in minimum requests to db. This works because everything is in one transaction
//  TODO: when I start writing tests, check if I have already one player in db
//    public void create(CreateFinishedMatchRequestDTO requestDTO) {
//        log.info("Tyring to find first player={}", requestDTO.firstPlayerName());
//        PlayerEntity player1 = playerDao.findByName(requestDTO.firstPlayerName())
//                .orElseGet(() -> {
////                    log.info("Player={} not found", requestDTO.firstPlayerName());
//                    return new PlayerEntity(requestDTO.firstPlayerName());
//                });
//        log.info("Tyring to find second player={}", requestDTO.secondPlayerName());
//        PlayerEntity player2 = playerDao.findByName(requestDTO.secondPlayerName())
//                .orElse(new PlayerEntity(requestDTO.secondPlayerName()));
//
//        log.info("First player: id={}, name={}", player1.getId(), player1.getName());
//        log.info("Second player: id={}, name{}", player2.getId(), player2.getName());
//
//        player1 = playerDao.save(player1);
//        player2 = playerDao.save(player2);
//
//        PlayerEntity winner = getWinner(player1, player2, requestDTO.winnerName());
//
//        MatchEntity match = new MatchEntity(player1, player2, winner);
//        matchDao.save(match);
//        log.info("Match with players: {} and {} is saved", player1.getName(), player2.getName());
//    }

    public void create(CreateFinishedMatchRequestDTO requestDTO) {
        // TODO: this all needs to be extracted to some validation class? or method? or in custom validator?
        String firstPlayerName = requestDTO.firstPlayerName();
        String secondPlayerName = requestDTO.secondPlayerName();
        String winnerName = requestDTO.winnerName();
        if(firstPlayerName.equals(secondPlayerName)) {
            throw new SamePlayersException(requestDTO.firstPlayerName());
        }
        if(!firstPlayerName.equals(winnerName)
            && !secondPlayerName.equals(winnerName)) {
            throw new IllegalStateException(
                    "Unknown winner. The winner's name does not match the name of the players."
            );
        }

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            PlayerEntity player1 = playerDao.findOrCreate(em, requestDTO.firstPlayerName());
            PlayerEntity player2 = playerDao.findOrCreate(em, requestDTO.secondPlayerName());
            PlayerEntity winner = firstPlayerName.equals(winnerName) ? player1 : player2;

            MatchEntity match = new MatchEntity(player1, player2, winner);
            matchDao.save(em, match);
            em.getTransaction().commit();
            log.info("Match with players: {} and {} is saved", player1.getName(), player2.getName());
        } catch (Exception e) {
            log.info("Caught exception: {}; executing rollback....", e.getMessage());
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }

    private PlayerEntity getWinner(PlayerEntity player1, PlayerEntity player2, String winnerName) {
        if(winnerName.equals(player1.getName())) {
            return player1;
        } else if(winnerName.equals(player2.getName())) {
            return player2;
        }
        throw new IllegalStateException(
                "Unknown winner. The winner's name does not match the name of the players."
        );
    }
}
