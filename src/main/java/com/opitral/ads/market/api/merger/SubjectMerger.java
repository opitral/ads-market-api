package com.opitral.ads.market.api.merger;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import com.opitral.ads.market.api.domain.entity.SubjectEntity;
import com.opitral.ads.market.api.model.view.SubjectView;

@Component
@RequiredArgsConstructor
public class SubjectMerger implements Merger<SubjectEntity, SubjectView> {
    @Override
    public void mergeCreate(SubjectEntity entity, SubjectView view) { mergeMainFields(entity, view); }

    @Override
    public void mergeEdit(SubjectEntity entity, SubjectView view) {
        mergeMainFields(entity, view);
    }

    private void mergeMainFields(SubjectEntity entity, SubjectView view) {
        if (view.getNameUa() != null)
            entity.setNameUa(view.getNameUa());

        if (view.getNameRu() != null)
            entity.setNameRu(view.getNameRu());

        if (view.getNameEn() != null)
            entity.setNameEn(view.getNameEn());
    }
}
