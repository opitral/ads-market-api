package com.opitral.ads.market.api.repositories;

import com.opitral.ads.market.api.domain.entity.SubjectEntity;

public interface SubjectRepository extends BaseRepository<SubjectEntity> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Integer id);
}
