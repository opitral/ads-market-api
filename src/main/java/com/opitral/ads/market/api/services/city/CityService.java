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
import com.opitral.ads.market.api.model.response.CityResponse;
import com.opitral.ads.market.api.model.view.CityView;
import com.opitral.ads.market.api.services.BaseService;
import com.opitral.ads.market.api.services.subject.SubjectService;

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

    public CityListResponse getAllCities(String restrict) {
        List<CityResponse> responseList = getList(parse(restrict)).stream()
                .map(this::buildCityResponseDto)
                .collect(Collectors.toList());

        return new CityListResponse(count(restrict), responseList);
    }

    public CityResponse buildCityResponseDto(CityEntity entity) {
        return CityResponse.builder()
                .id(entity.getId())
                .nameUa(entity.getNameUa())
                .nameRu(entity.getNameRu())
                .nameEn(entity.getNameEn())
                .subjectId(entity.getSubjectId())
                .build();
    }
}
