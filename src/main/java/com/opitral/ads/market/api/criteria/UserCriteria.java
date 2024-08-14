package com.opitral.ads.market.api.criteria;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import lombok.Getter;
import lombok.Setter;

import com.opitral.ads.market.api.domain.entity.UserEntity;
import com.opitral.ads.market.api.domain.entity.UserEntity_;

@Getter
@Setter
public class UserCriteria extends Criteria<UserEntity> {

    private List<Integer> ids;
    private String query;
    private String telegramId;

    public UserCriteria() { super(UserEntity.class); }

    public UserCriteria(String restrict) {
        super(UserEntity.class);

        UserCriteria parsed = parse(restrict, UserCriteria.class);
        if (parsed != null) {
            this.ids = parsed.ids;
            this.query = parsed.query;
            this.telegramId = parsed.telegramId;
        }
    }

    @Override
    public List<Predicate> query(Root<UserEntity> root, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (ids != null && !ids.isEmpty()) {
            predicates.add(root.get(UserEntity_.id).in(ids));
        }

        if (query != null && !query.isEmpty()) {
            String likeQuery = '%' + query.toLowerCase() + '%';
            predicates.add(cb.or(
                    cb.like(cb.lower(root.get(UserEntity_.telegramId)), likeQuery),
                    cb.like(cb.lower(root.get(UserEntity_.firstName)), likeQuery),
                    cb.like(cb.lower(root.get(UserEntity_.lastName)), likeQuery)
            ));
        }

        if (telegramId != null)
            predicates.add(cb.equal(root.get(UserEntity_.telegramId), telegramId));

        return predicates;
    }
}
