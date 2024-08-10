package com.opitral.ads.market.api.services.subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opitral.ads.market.api.domain.entity.SubjectEntity;
import com.opitral.ads.market.api.services.BaseValidator;
import com.opitral.ads.market.api.exception.ValidationException;
import com.opitral.ads.market.api.repositories.SubjectRepository;
import com.opitral.ads.market.api.domain.enums.PermissionsEnum;
import static com.opitral.ads.market.api.security.SecurityContextAccessor.requirePermission;

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
//        requirePermission(PermissionsEnum.MANAGE_SUBJECTS);
        if (
                subjectRepository.existsByNameUa(entity.getNameUa()) ||
                        subjectRepository.existsByNameRu(entity.getNameRu()) ||
                        subjectRepository.existsByNameEn(entity.getNameEn())
        )
            throw new ValidationException(SubjectEntity.class.getName(), "error.subject.name.unique");
    }

    @Override
    public void validForUpdate(SubjectEntity entity) {
        super.validForUpdate(entity);
//        requirePermission(PermissionsEnum.MANAGE_SUBJECTS);
        if (
                subjectRepository.existsByNameUaEqualsAndIdNot(entity.getNameUa(), entity.getId()) ||
                        subjectRepository.existsByNameRuEqualsAndIdNot(entity.getNameRu(), entity.getId()) ||
                        subjectRepository.existsByNameEnEqualsAndIdNot(entity.getNameEn(), entity.getId())
        )
            throw new ValidationException(SubjectEntity.class.getName(), "error.subject.name.unique");
    }

    @Override
    public void validForDelete(SubjectEntity entity) {
        super.validForDelete(entity);
//        requirePermission(PermissionsEnum.DELETE_SUBJECTS);

    }
}