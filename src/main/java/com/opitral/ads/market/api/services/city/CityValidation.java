package com.opitral.ads.market.api.services.city;

import org.springframework.stereotype.Service;

import com.opitral.ads.market.api.domain.entity.CityEntity;
import com.opitral.ads.market.api.domain.entity.SubjectEntity;
import com.opitral.ads.market.api.exception.ValidationException;
import com.opitral.ads.market.api.repositories.CityRepository;
import com.opitral.ads.market.api.services.BaseValidator;

@Service
public class CityValidation extends BaseValidator<CityEntity> {

    private final CityRepository cityRepository;

    public CityValidation(final CityRepository cityRepository) {
        super(CityEntity.class);
        this.cityRepository = cityRepository;
    }

    @Override
    public void validForCreate(CityEntity entity) {
        super.validForCreate(entity);
        if (
                cityRepository.existsByNameUaAndSubjectId(entity.getNameUa(), entity.getSubjectId()) ||
                        cityRepository.existsByNameRuAndSubjectId(entity.getNameRu(), entity.getSubjectId()) ||
                        cityRepository.existsByNameEnAndSubjectId(entity.getNameEn(), entity.getSubjectId())
        )
            throw new ValidationException(SubjectEntity.class.getName(), "error.city.name.unique");
    }

    @Override
    public void validForUpdate(CityEntity entity) {
        super.validForUpdate(entity);
        if (
                cityRepository.existsByNameUaAndSubjectIdAndIdNot(entity.getNameUa(), entity.getSubjectId(), entity.getId()) ||
                        cityRepository.existsByNameRuAndSubjectIdAndIdNot(entity.getNameRu(), entity.getSubjectId(), entity.getId()) ||
                        cityRepository.existsByNameEnAndSubjectIdAndIdNot(entity.getNameEn(), entity.getSubjectId(), entity.getId())
        )
            throw new ValidationException(SubjectEntity.class.getName(), "error.city.name.unique");
    }

    @Override
    public void validForDelete(CityEntity entity) {
        super.validForDelete(entity);
    }
}