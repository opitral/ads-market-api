package com.opitral.ads.market.api.merger;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import com.opitral.ads.market.api.domain.entity.CityEntity;
import com.opitral.ads.market.api.domain.entity.SubjectEntity;
import com.opitral.ads.market.api.exception.NoSuchEntityException;
import com.opitral.ads.market.api.model.view.CityView;
import com.opitral.ads.market.api.repositories.SubjectRepository;

@Component
@RequiredArgsConstructor
public class CityMerger implements Merger<CityEntity, CityView> {

    private final SubjectRepository subjectRepository;

    @Override
    public void mergeCreate(CityEntity entity, CityView view) {
        mergeMainFields(entity, view);
    }

    @Override
    public void mergeEdit(CityEntity entity, CityView view) {
        if (view.getId() != null)
            entity.setId(view.getId());

        mergeMainFields(entity, view);
    }

    private void mergeMainFields(CityEntity entity, CityView view) {
        if (view.getNameUa() != null)
            entity.setNameUa(view.getNameUa());

        if (view.getNameRu() != null)
            entity.setNameRu(view.getNameRu());

        if (view.getNameEn() != null)
            entity.setNameEn(view.getNameEn());

        if (view.getSubjectId() != null) {
            entity.setSubject(subjectRepository.findById(view.getSubjectId()).orElseThrow(
                    () -> new NoSuchEntityException(SubjectEntity.class.getName(), "by id: " + view.getSubjectId())
            ));
            entity.setSubjectId(view.getSubjectId());
        }
    }
}