package dev.backend.dao;

import dev.backend.entity.MatchEntity;
import dev.backend.entity.PlayerEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class MatchDao {
    private final EntityManagerFactory emf;

    public MatchDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

//    public void create(PlayerEntity player1, PlayerEntity player2) {
    public void save(MatchEntity match) {
        EntityManager em = emf.createEntityManager();

        // TODO: Extract the try/catch block into a separate class (TransactionHandler).
        try {
            em.getTransaction().begin(); //TODO: this throws error(check docs) if isActive() is true, so we can't have nested transaction. If we want to have transactions in service, then we need to check here if we already in transaction.
            em.persist(match);

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
