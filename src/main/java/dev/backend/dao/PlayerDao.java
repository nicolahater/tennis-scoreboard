package dev.backend.dao;

import dev.backend.entity.PlayerEntity;
import jakarta.persistence.EntityExistsException;
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

    public PlayerEntity save(PlayerEntity player) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            if(player.getId() == null) em.persist(player);
//            else player = em.merge(player);

            em.getTransaction().commit();

            return player;
        } catch (EntityExistsException e) {
            System.out.println("Caught exception: " + e + "; executing rollback...."); // TODO: replace with log
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return player;
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
}
