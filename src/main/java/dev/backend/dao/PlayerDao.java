package dev.backend.dao;

import dev.backend.entity.PlayerEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class PlayerDao {
    private final EntityManagerFactory emf;

    public PlayerDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    // TODO: make it Optional<PlayerEntity>
    public PlayerEntity findByName(String name) {
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

            return playerEntity;
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
}
