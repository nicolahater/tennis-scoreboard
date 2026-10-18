package dev.backend.dao;

import dev.backend.entity.PlayerEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PlayerDao {
    private final EntityManagerFactory emf;

    public PlayerDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Optional<PlayerEntity> findByName(String name) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            String sql = """
                    select p
                    from PlayerEntity p
                    where p.name = :name
                    """;
            PlayerEntity playerEntity = em
                    .createQuery(sql, PlayerEntity.class)
                    .setParameter("name", name)
                    .getSingleResultOrNull();

            em.getTransaction().commit();

            return Optional.ofNullable(playerEntity);
        } catch (Exception e) {
            System.out.println("Caught exception: " + e + "; executing rollback...."); // TODO: replace with log
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    // TODO: return Optional<PlayerEntity>
//    public PlayerEntity create(String name) {
    public PlayerEntity save(PlayerEntity player) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            PlayerEntity p = findByName(player.getName());
            if(p == null) em.persist(player);
            else player = p;

            em.getTransaction().commit();

            return player;
        } catch (Exception e) {
            System.out.println("Caught exception: " + e + "; executing rollback...."); // TODO: replace with log
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
        }

        return null;
    }


    //TODO: this is repository method
    public PlayerEntity create(String playerName) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            PlayerEntity player = findByName(playerName);
            if(player == null) {
                player = new PlayerEntity(playerName);
                em.persist(player);
            }

            em.getTransaction().commit();

            return player;
        } catch (Exception e) {
            System.out.println("Caught exception: " + e + "; executing rollback...."); // TODO: replace with log
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
        }

        return null;
    }
}
