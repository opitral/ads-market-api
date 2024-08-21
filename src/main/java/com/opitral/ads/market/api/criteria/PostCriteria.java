package com.opitral.ads.market.api.criteria;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import com.opitral.ads.market.api.domain.entity.PostEntity;
import com.opitral.ads.market.api.domain.entity.PostEntity_;

@Getter
@Setter
public class PostCriteria extends Criteria<PostEntity> {

    private List<Integer> ids;
    private String query;

    public PostCriteria() { super(PostEntity.class); }

    public PostCriteria(String restrict) {
        super(PostEntity.class);

        PostCriteria parsed = parse(restrict, PostCriteria.class);
        if (parsed != null) {
            this.ids = parsed.ids;
            this.query = parsed.query;
        }
    }

    @Override
    public List<Predicate> query(Root<PostEntity> root, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (ids != null && !ids.isEmpty())
            predicates.add(root.get(PostEntity_.id).in(ids));

        return predicates;
    }
}
