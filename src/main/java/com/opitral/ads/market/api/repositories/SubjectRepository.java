package com.opitral.ads.market.api.repositories;

import com.opitral.ads.market.api.domain.entity.SubjectEntity;

public interface SubjectRepository extends BaseRepository<SubjectEntity> {
    boolean existsByNameUa(String nameUa);
    boolean existsByNameUaEqualsAndIdNot(String nameUa, Integer id);

    boolean existsByNameRu(String nameRu);
    boolean existsByNameRuEqualsAndIdNot(String nameRu, Integer id);

    boolean existsByNameEn(String nameEn);
    boolean existsByNameEnEqualsAndIdNot(String nameEn, Integer id);
}
