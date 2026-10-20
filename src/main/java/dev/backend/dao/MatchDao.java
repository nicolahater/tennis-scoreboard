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
    private static final String FIND_MATCHES_BY_NAME_QUERY = """
            select m
            from MatchEntity m
            join fetch m.playerEntity1
            join fetch m.playerEntity2
            where upper(m.playerEntity1.name) = upper(:name)
                  or upper(m.playerEntity2.name) = upper(:name)
                  or :name is null
            order by m.id desc
            """;
    private static final String TOTAL_MATCHES_NUMBER_BY_NAME_QUERY = """
            select count(m)
            from MatchEntity m
            where upper(m.playerEntity1.name) = upper(:name)
                  or upper(m.playerEntity2.name) = upper(:name)
                  or :name is null
            """;


    public MatchDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    // TODO:
//    public MyPage<MatchEntity> getAll(Integer page, Integer size) {
//        baseGetAll() // return criteria query or builder
//        // then make here request without where clause
//    }
//
//    public MyPage<MatchEntity> getAll(String name, Integer page, Integer size) {
//        baseGetAll() // return criteria query or builder. some like that root is from(MatchEntity order by -- this is the base
//        // then append to it where clause
//        // then make reqest
//    }
//
//
//    public MyPage<MatchEntity> baseGetAll(CriteriaQuery/CriteriaBuilder, Integer page, Integer size)
    // TODO: also instead of "String name" I can accept "Object filterClause" and checking instances of these objects or it is possible to make like this using map: https://stackoverflow.com/questions/965690/how-do-i-use-optional-parameters-in-java


    // TODO: `page` and `size` in Pageable
    public MyPage<MatchEntity> getAll(EntityManager em, String name, Integer page, Integer size) {
        // TODO: it calculation should be in service layer
        int offset = (page-1) * size;

        // TODO: use CriteriaBuilder later

        List<MatchEntity> matches = em.createQuery(FIND_MATCHES_BY_NAME_QUERY, MatchEntity.class)
                .setParameter("name", name)
                .setMaxResults(size)    // "limit" alternative
                .setFirstResult(offset) // "offset" alternative
                .getResultList();

        Long totalMatches = em.createQuery(TOTAL_MATCHES_NUMBER_BY_NAME_QUERY, Long.class)
                .setParameter("name", name)
                .getSingleResult();

        return new MyPage<>(matches, page, totalMatches, size);
    }

    public void save(EntityManager em, MatchEntity match) {
//        if(match.getId() != null) em.persist(match);
//        else em.merge(match);
        em.persist(match);
    }
}
