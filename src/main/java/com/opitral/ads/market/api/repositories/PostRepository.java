package com.opitral.ads.market.api.repositories;

import java.time.LocalDate;
import java.util.List;

import com.opitral.ads.market.api.domain.entity.PostEntity;

public interface PostRepository extends BaseRepository<PostEntity> {
    List<PostEntity> findAllByGroupIdAndPublishDateBetween(Integer groupId, LocalDate startDate, LocalDate endDate);
}
