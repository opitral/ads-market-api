package com.opitral.ads.market.api.services.city;

import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.opitral.ads.market.api.criteria.Criteria;
import com.opitral.ads.market.api.criteria.CityCriteria;
import com.opitral.ads.market.api.domain.entity.CityEntity;
import com.opitral.ads.market.api.exception.BaseException;
import com.opitral.ads.market.api.exception.NoSuchEntityException;
import com.opitral.ads.market.api.model.response.CityListResponse;
import com.opitral.ads.market.api.model.response.CityListResponseLocalized;
import com.opitral.ads.market.api.model.response.CityResponse;
import com.opitral.ads.market.api.model.response.CityResponseLocalized;
import com.opitral.ads.market.api.model.view.CityView;
import com.opitral.ads.market.api.services.BaseService;
import com.opitral.ads.market.api.services.subject.SubjectService;
import com.opitral.ads.market.api.utils.Utils;

@Service
@Slf4j
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BaseException.class)
public class CityService extends BaseService<CityEntity, CityView> {

    private final SubjectService subjectService;

    public CityService(SubjectService subjectService) {
        super(CityEntity.class, CityEntity::new);
        this.subjectService = subjectService;
    }

    @Override
    public Criteria<CityEntity> parse(String restrict) {
        return new CityCriteria(restrict);
    }

    @Override
    public boolean delete(Integer id) {
        CityEntity entity = repository.findById(id).orElseThrow(
                () -> new NoSuchEntityException(CityEntity.class.getName(), "by id: " + id)
        );
        validationService.validForDelete(entity);
        repository.delete(entity);
        log.debug("Deleted city: {}", entity);

        return true;
    }

    public CityResponse getCityById(Integer id) {
        return buildCityResponseDto(getById(id));
    }

    public CityResponseLocalized getCityByIdLocalized(Integer id, String locale) {
        return buildCityResponseLocalizedDto(getById(id), locale);
    }

    public CityListResponse getAllCities(String restrict) {
        List<CityResponse> responseList = getList(parse(restrict)).stream()
                .map(this::buildCityResponseDto)
                .collect(Collectors.toList());

        return new CityListResponse(count(restrict), responseList);
    }

    public CityListResponseLocalized getAllCitiesLocalized(String restrict, String locale) {
        List<CityResponseLocalized> responseList = getList(parse(restrict)).stream()
                .map(entity -> buildCityResponseLocalizedDto(entity, locale))
                .collect(Collectors.toList());

        return new CityListResponseLocalized(count(restrict), responseList);
    }

    public CityResponseLocalized buildCityResponseLocalizedDto(CityEntity entity, String locale) {
        return CityResponseLocalized.builder()
                .id(entity.getId())
                .name(Utils.getLocalizedValue(entity::getNameUa, entity::getNameRu, entity::getNameEn, locale))
                .subject(subjectService.buildSubjectResponseLocalizedDto(entity.getSubject(), locale))
                .build();
    }

    public CityResponse buildCityResponseDto(CityEntity entity) {
        return CityResponse.builder()
                .id(entity.getId())
                .nameUa(entity.getNameUa())
                .nameRu(entity.getNameRu())
                .nameEn(entity.getNameEn())
                .subject(subjectService.buildSubjectResponseDto(entity.getSubject()))
                .build();
    }
}

