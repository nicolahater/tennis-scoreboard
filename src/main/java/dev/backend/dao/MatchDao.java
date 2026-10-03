package dev.backend.dao;

import dev.backend.dto.CreateMatchRequestDTO;
import dev.backend.entity.Match;
import dev.backend.entity.Player;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class MatchDao {
    private final EntityManagerFactory emf;

    public MatchDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void createMatch(Player player1, Player player2) {
        EntityManager em = emf.createEntityManager();

        // TODO: Extract the try/catch block into a separate class (TransactionHandler).
        try {
            em.getTransaction().begin();

            Match newMatch = new Match(player1, player2);
            em.persist(newMatch);

            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Caught exception: " + e + "; executing rollback...."); // TODO: replace with log
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
        }


    }
}
