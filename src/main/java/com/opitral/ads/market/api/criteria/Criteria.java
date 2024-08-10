package com.opitral.ads.market.api.criteria;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import jakarta.persistence.TypedQuery;
import jakarta.annotation.Nullable;
import jakarta.persistence.metamodel.SingularAttribute;

import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.opitral.ads.market.api.domain.enums.OrderDirectionEnum;
import com.opitral.ads.market.api.exception.WrongRestrictionException;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class Criteria<T> {
    private int offset;
    private int limit;
    private String order_by = "id";
    private OrderDirectionEnum order_direction = OrderDirectionEnum.ASC;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final Class<T> entityClass;

    /**
     * implement this to set query params
     *
     * @param root - entity root.
     *             use <code>root.get("field_name")</code> to get the field from root entity
     * @param cb - create main query expressions
     *
     * @return list of predicates to be applied to query
     */
    public abstract List<Predicate> query(Root<T> root, CriteriaBuilder cb);

    protected void fetch(CriteriaBuilder cb, Root<T> root) {}

    public TypedQuery<T> createQuery(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(entityClass);

        Root<T> root = query.from(entityClass);
        query.select(root);
        query.distinct(true);
        List<Predicate> predicates = query(root, cb);
        if (!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        query.orderBy(formOrder(cb, root));
        fetch(cb, root);

        return em.createQuery(query);
    }

    public TypedQuery<Long> createCountQuery(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);

        Root<T> root = query.from(entityClass);
        query.select(cb.count(root));
        query.distinct(true);
        List<Predicate> predicates = query(root, cb);
        if (!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        return em.createQuery(query);
    }

    private Order formOrder(CriteriaBuilder cb, Root<T> root) {
        Order order;

        if (order_by != null && !order_by.isEmpty()) {
            if (order_direction != null && order_direction == OrderDirectionEnum.DESC) {
                order = cb.desc(getAttributePath(root, order_by));
            } else {
                order = cb.asc(getAttributePath(root, order_by));
            }
        } else {
            order = cb.asc(root.get("id"));
        }

        return order;
    }

    protected Path<?> getAttributePath(Root<T> root, String attributeName) {
        return root.get(attributeName);
    }

    /**
     * return null if {#restriction} is empty or null
     *
     * @param restriction - json representation of object
     * @param clazz - class of object
     * @param <U> - type that extend Criteria
     * @return parsed criteria
     * @throws WrongRestrictionException - if passed wrong json format
     */
    protected <U extends Criteria<T>> U parse(String restriction, Class<U> clazz) {
        if (restriction == null || restriction.isEmpty() || restriction.equals("{}"))
            return null;

        try {
            ObjectMapper objectMapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            U parsed = objectMapper.readValue(restriction, clazz);

            if (parsed.getLimit() > 0) {
                setLimit(parsed.getLimit());
            }

            if (parsed.getOffset() > 0) {
                setOffset(parsed.getOffset());
            }

            setOrder_by(parsed.getOrder_by());
            setOrder_direction(parsed.getOrder_direction());

            return parsed;
        } catch (Exception e) {
            throw new WrongRestrictionException(e);
        }
    }

    protected <K> List<Predicate> equalsOrInPredicates(
            CriteriaBuilder cb, Root<T> root, SingularAttribute<T, K> attribute, @Nullable K equalValue, @Nullable Collection<K> inValue
    ) {
        List<Predicate> predicates = new ArrayList<>();
        if (equalValue != null) {
            predicates.add(cb.equal(root.get(attribute), equalValue));
        } else if (!CollectionUtils.isEmpty(inValue)) {
            predicates.add(root.get(attribute).in(inValue));
        }

        return predicates;
    }
}
