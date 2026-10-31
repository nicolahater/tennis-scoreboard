package dev.backend.dao;

import dev.backend.entity.PlayerEntity;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PlayerDao {
    private final EntityManagerFactory emf;
//    @PersistenceContext
//    private EntityManager em;

    private static final Logger log = LoggerFactory.getLogger(PlayerDao.class);
    private static final String FIND_PLAYER_BY_NAME_QUERY = """
                select p
                from PlayerEntity p
                where p.name = :name
                """;

    public PlayerDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public PlayerEntity findOrCreate(EntityManager em, String playerName) {
        log.info("Tyring to find player={}....", playerName);
        return findByName(em, playerName)
                .map(player -> {
                    log.info("Player={} found", playerName);
                    return player;
                })
                .orElseGet(() -> {
                    log.info("Player={} not found, saving in db....", playerName);
                    return save(em, new PlayerEntity(playerName));
                });
    }


    public Optional<PlayerEntity> findByName(EntityManager em, String name) {
        PlayerEntity playerEntity = em
                .createQuery(FIND_PLAYER_BY_NAME_QUERY, PlayerEntity.class)
                .setParameter("name", name)
                .getSingleResultOrNull();

        return Optional.ofNullable(playerEntity);
    }

    public PlayerEntity save(EntityManager em, PlayerEntity player) {
        if(player.getId() == null) em.persist(player);
//        else  player = em.merge(player);
        return player;
    }
}
