package dev.backend.dao;

import dev.backend.entity.MatchEntity;
import dev.backend.utils.MyPage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MatchDao {
    private final EntityManagerFactory emf;

    public MatchDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    // TODO: `page` and `size` in Pageable
    public MyPage<MatchEntity> getAll(String name, Integer page, Integer size) {
        int offset = (page-1) * size;

        // TODO: use CriteriaBuilder later
        String getAllHQL = """
            select m
            from MatchEntity m
            join fetch m.playerEntity1
            join fetch m.playerEntity2
            where upper(m.playerEntity1.name) = upper(:name)
                  or upper(m.playerEntity2.name) = upper(:name)
                  or :name is null
            order by m.id desc
        """;

        String getTotalHQL = """
            select count(m)
            from MatchEntity m
            where upper(m.playerEntity1.name) = upper(:name)
                  or upper(m.playerEntity2.name) = upper(:name)
                  or :name is null
        """;

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            List<MatchEntity> matches = em.createQuery(getAllHQL, MatchEntity.class)
                    .setParameter("name", name)
                    .setMaxResults(size)    // "limit" alternative
                    .setFirstResult(offset) // "offset" alternative
                    .getResultList();

            Long totalMatches = em.createQuery(getTotalHQL, Long.class)
                    .setParameter("name", name)
                    .getSingleResult();

            em.getTransaction().commit();


            return new MyPage<>(matches, page, totalMatches, size);
        } catch (Exception e) {
            System.out.println("Caught exception: " + e + "; executing rollback....");
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void save(MatchEntity match) {
        EntityManager em = emf.createEntityManager();

        // TODO: Extract the try/catch block into a separate class (TransactionHandler). Maybe use proxy pattern to implement this. Also transaction has to be in service layer.
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
