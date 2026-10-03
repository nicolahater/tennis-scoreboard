package dev.backend.dao;

import dev.backend.entity.Player;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class PlayerDao {
    private final EntityManagerFactory emf;

    public PlayerDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Player findByName(String name) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            String sql = """
                    select p
                    from Player p
                    where p.name = :name
                    """;
            Player player = em
                    .createQuery(sql, Player.class)
                    .setParameter("name", name)
                    .getSingleResult();

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

    // TODO: I can try to create a player and if this player is already in db, then i catch the error and in this catch block I get that player and return
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
