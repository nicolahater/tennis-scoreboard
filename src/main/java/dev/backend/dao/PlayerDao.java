package dev.backend.dao;

import dev.backend.entity.Player;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class PlayerDao {
    private final EntityManagerFactory emf;

    public PlayerDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Player create(String name) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Player newPlayer = new Player(name);
            em.persist(newPlayer);

            em.getTransaction().commit();

            return newPlayer;
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
