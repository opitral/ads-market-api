package com.opitral.ads.market.api.repositories;

import java.time.LocalDate;
import java.time.LocalTime;

import com.opitral.ads.market.api.domain.entity.PostEntity;

public interface PostRepository extends BaseRepository<PostEntity> {
    boolean existsByGroupIdAndPublishDateAndPublishTime(Integer groupId, LocalDate publishDate, LocalTime publishTime);
}
