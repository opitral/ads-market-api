package com.opitral.ads.market.api.repositories;

import com.opitral.ads.market.api.domain.entity.CityEntity;

public interface CityRepository extends BaseRepository<CityEntity> {
    boolean existsByNameAndSubjectId(String name, Integer subjectId);
    boolean existsByNameAndSubjectIdAndIdNot(String name, Integer subjectId, Integer id);
}
