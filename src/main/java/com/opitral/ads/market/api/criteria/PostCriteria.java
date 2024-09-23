package com.opitral.ads.market.api.criteria;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.opitral.ads.market.api.domain.enums.PostStatus;
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
    private LocalDate publishDate;
    private LocalTime publishTime;
    private PostStatus status;
    private String groupTelegramId;
    private Integer messageId;

    public PostCriteria() { super(PostEntity.class); }

    public PostCriteria(String restrict) {
        super(PostEntity.class);

        PostCriteria parsed = parse(restrict, PostCriteria.class);
        if (parsed != null) {
            this.ids = parsed.ids;
            this.query = parsed.query;
            this.publishDate = parsed.publishDate;
            this.publishTime = parsed.publishTime;
            this.status = parsed.status;
            this.groupTelegramId = parsed.groupTelegramId;
            this.messageId = parsed.messageId;
        }
    }

    @Override
    public List<Predicate> query(Root<PostEntity> root, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (ids != null && !ids.isEmpty())
            predicates.add(root.get(PostEntity_.id).in(ids));

        if (publishDate != null)
            predicates.add(cb.equal(root.get(PostEntity_.publishDate), publishDate));

        if (publishTime != null)
            predicates.add(cb.equal(root.get(PostEntity_.publishTime), publishTime));

        if (status != null)
            predicates.add(cb.equal(root.get(PostEntity_.status), status));

        if (groupTelegramId != null)
            predicates.add(cb.equal(root.get(PostEntity_.groupTelegramId), groupTelegramId));

        if (messageId != null)
            predicates.add(cb.equal(root.get(PostEntity_.messageId), messageId));

        return predicates;
    }
}
