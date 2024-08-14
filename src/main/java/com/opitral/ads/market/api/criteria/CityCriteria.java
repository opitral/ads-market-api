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
public class CityCriteria extends Criteria<CityEntity> {

    private List<Integer> ids;
    private String query;
    private Integer subjectId;

    public CityCriteria() { super(CityEntity.class); }

    public CityCriteria(String restrict) {
        super(CityEntity.class);

        CityCriteria parsed = parse(restrict, CityCriteria.class);
        if (parsed != null) {
            this.ids = parsed.ids;
            this.query = parsed.query;
            this.subjectId = parsed.subjectId;
        }
    }

    @Override
    public List<Predicate> query(Root<CityEntity> root, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (ids != null && !ids.isEmpty())
            predicates.add(root.get(CityEntity_.id).in(ids));

        if (query != null && !query.isEmpty()) {
            String likeQuery = '%' + query.toLowerCase() + '%';
            predicates.add(cb.or(
                    cb.like(cb.lower(root.get(CityEntity_.nameUa)), likeQuery),
                    cb.like(cb.lower(root.get(CityEntity_.nameRu)), likeQuery),
                    cb.like(cb.lower(root.get(CityEntity_.nameEn)), likeQuery)
            ));
        }

        if (subjectId != null)
            predicates.add(cb.equal(root.get(CityEntity_.subjectId), subjectId));

        return predicates;
    }
}
