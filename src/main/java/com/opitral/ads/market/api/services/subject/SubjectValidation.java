package com.opitral.ads.market.api.services.subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opitral.ads.market.api.domain.entity.SubjectEntity;
import com.opitral.ads.market.api.services.BaseValidator;
import com.opitral.ads.market.api.exception.ValidationException;
import com.opitral.ads.market.api.repositories.SubjectRepository;

@Service
public class SubjectValidation extends BaseValidator<SubjectEntity> {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectValidation(final SubjectRepository subjectRepository) {
        super(SubjectEntity.class);
        this.subjectRepository = subjectRepository;
    }

    @Override
    public void validForCreate(SubjectEntity entity) {
        super.validForCreate(entity);
        if (subjectRepository.existsByName(entity.getName()))
            throw new ValidationException(SubjectEntity.class.getName(), "Напрпавление с таким именем уже созданно");
    }

    @Override
    public void validForUpdate(SubjectEntity entity) {
        super.validForUpdate(entity);
        if (subjectRepository.existsByNameAndIdNot(entity.getName(), entity.getId()))
            throw new ValidationException(SubjectEntity.class.getName(), "Напрпавление с таким именем уже созданно");
    }

    @Override
    public void validForDelete(SubjectEntity entity) {
        super.validForDelete(entity);
    }
}