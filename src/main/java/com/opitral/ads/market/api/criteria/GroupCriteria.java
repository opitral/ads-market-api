package com.opitral.ads.market.api.criteria;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import com.opitral.ads.market.api.domain.entity.GroupEntity;
import com.opitral.ads.market.api.domain.entity.GroupEntity_;

@Getter
@Setter
public class GroupCriteria extends Criteria<GroupEntity> {

    private List<Integer> ids;
    private String query;
    private String groupTelegramId;
    private String userTelegramId;
    private Integer cityId;

    public GroupCriteria() { super(GroupEntity.class); }

    public GroupCriteria(String restrict) {
        super(GroupEntity.class);

        GroupCriteria parsed = parse(restrict, GroupCriteria.class);
        if (parsed != null) {
            this.ids = parsed.ids;
            this.query = parsed.query;
            this.groupTelegramId = parsed.groupTelegramId;
            this.userTelegramId = parsed.userTelegramId;
            this.cityId = parsed.cityId;
        }
    }

    @Override
    public List<Predicate> query(Root<GroupEntity> root, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (ids != null && !ids.isEmpty())
            predicates.add(root.get(GroupEntity_.id).in(ids));

        if (query != null && !query.isEmpty()) {
            String likeQuery = '%' + query.toLowerCase() + '%';
            predicates.add(cb.or(
                    cb.like(cb.lower(root.get(GroupEntity_.name)), likeQuery),
                    cb.like(cb.lower(root.get(GroupEntity_.link)), likeQuery),
                    cb.like(cb.lower(root.get(GroupEntity_.groupTelegramId)), likeQuery),
                    cb.like(cb.lower(root.get(GroupEntity_.userTelegramId)), likeQuery)
            ));
        }

        if (groupTelegramId != null)
            predicates.add(cb.equal(root.get(GroupEntity_.groupTelegramId), groupTelegramId));

        if (userTelegramId != null)
            predicates.add(cb.equal(root.get(GroupEntity_.userTelegramId), userTelegramId));

        if (cityId != null)
            predicates.add(cb.equal(root.get(GroupEntity_.cityId), cityId));

        return predicates;
    }
}
