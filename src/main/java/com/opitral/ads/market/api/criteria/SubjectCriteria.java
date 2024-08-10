package com.opitral.ads.market.api.criteria;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import com.opitral.ads.market.api.domain.entity.*;

@Getter
@Setter
public class SubjectCriteria extends LocalizedCriteria<SubjectEntity> {
    private static final Set<String> LOCALIZED_FIELDS = new HashSet<>(List.of(
            "name"
    ));

    private List<Integer> ids;
    private String query;

    public SubjectCriteria() { super(SubjectEntity.class); }

    public SubjectCriteria(String restrict) { this(restrict, "ua"); }

    public SubjectCriteria(String restrict, String locale) {
        super(SubjectEntity.class, locale);

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

        if (query != null) {
            String likeQuery = '%' + query.toLowerCase() + '%';
            predicates.add(cb.or(
                    cb.like(cb.lower(root.get(SubjectEntity_.nameUa)), likeQuery),
                    cb.like(cb.lower(root.get(SubjectEntity_.nameRu)), likeQuery),
                    cb.like(cb.lower(root.get(SubjectEntity_.nameEn)), likeQuery)
            ));
        }

        return predicates;
    }

    @Override
    public Set<String> getLocalizedFields() { return LOCALIZED_FIELDS; }
}
