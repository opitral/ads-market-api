package com.opitral.ads.market.api.repositories;

import com.opitral.ads.market.api.domain.entity.CityEntity;

public interface CityRepository extends BaseRepository<CityEntity> {
    boolean existsByNameUaAndSubjectId(String nameUa, Integer subjectId);
    boolean existsByNameRuAndSubjectId(String nameRu, Integer subjectId);
    boolean existsByNameEnAndSubjectId(String nameEn, Integer subjectId);

    boolean existsByNameUaAndSubjectIdAndIdNot(String nameUa, Integer subjectId, Integer id);
    boolean existsByNameRuAndSubjectIdAndIdNot(String nameRu, Integer subjectId, Integer id);
    boolean existsByNameEnAndSubjectIdAndIdNot(String nameEn, Integer subjectId, Integer id);
}
