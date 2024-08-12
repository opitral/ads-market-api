package com.opitral.ads.market.api.repositories;

import com.opitral.ads.market.api.domain.entity.SubjectEntity;

public interface SubjectRepository extends BaseRepository<SubjectEntity> {
    boolean existsByNameUa(String nameUa);
    boolean existsByNameRu(String nameRu);
    boolean existsByNameEn(String nameEn);

    boolean existsByNameUaAndIdNot(String nameUa, Integer id);
    boolean existsByNameRuAndIdNot(String nameRu, Integer id);
    boolean existsByNameEnAndIdNot(String nameEn, Integer id);
}
