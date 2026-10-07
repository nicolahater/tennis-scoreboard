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

    public PlayerEntity findByName(String name) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            String sql = """
                    select p
                    from Player p
                    where p.name = :name
                    """;
            PlayerEntity playerEntity = em
                    .createQuery(sql, PlayerEntity.class)
                    .setParameter("name", name)
                    .getSingleResult();

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

    // TODO: I can try to create a playerEntity and if this playerEntity is already in db, then i catch the error and in this catch block I get that playerEntity and return
    public PlayerEntity create(String name) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            PlayerEntity newPlayerEntity = new PlayerEntity(name);
            em.persist(newPlayerEntity);

            em.getTransaction().commit();

            return newPlayerEntity;
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
