package com.opitral.ads.market.api.repositories.criteria;

import java.util.List;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.opitral.ads.market.api.criteria.Criteria;

@Repository
public class CriteriaRepositoryImpl implements ICriteriaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public <T> List<T> find(Criteria<T> criteria) {
        TypedQuery<T> query = criteria.createQuery(entityManager);
        if(criteria.getOffset() > 0)
            query.setFirstResult(criteria.getOffset());
        if(criteria.getLimit() > 0)
            query.setMaxResults(criteria.getLimit());
        return query.getResultList();
    }

    @Override
    public <T> long count(Criteria<T> criteria) {
        TypedQuery<Long> query = criteria.createCountQuery(entityManager);
        return  query.getSingleResult();
    }
}
