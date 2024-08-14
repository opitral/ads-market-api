package com.opitral.ads.market.api.criteria;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import com.opitral.ads.market.api.domain.entity.*;

@Getter
@Setter
public class SubjectCriteria extends Criteria<SubjectEntity> {

    private List<Integer> ids;
    private String query;

    public SubjectCriteria() { super(SubjectEntity.class); }

    public SubjectCriteria(String restrict) {
        super(SubjectEntity.class);

        SubjectCriteria parsed = parse(restrict, SubjectCriteria.class);
        if (parsed != null) {
            this.ids = parsed.ids;
            this.query = parsed.query;
        }
    }

    @Override
    public List<Predicate> query(Root<SubjectEntity> root, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (ids != null && !ids.isEmpty())
            predicates.add(root.get(SubjectEntity_.id).in(ids));

        if (query != null && !query.isEmpty()) {
            String likeQuery = '%' + query.toLowerCase() + '%';
            predicates.add(cb.or(
                    cb.like(cb.lower(root.get(SubjectEntity_.nameUa)), likeQuery),
                    cb.like(cb.lower(root.get(SubjectEntity_.nameRu)), likeQuery),
                    cb.like(cb.lower(root.get(SubjectEntity_.nameEn)), likeQuery)
            ));
        }

        return predicates;
    }
}
