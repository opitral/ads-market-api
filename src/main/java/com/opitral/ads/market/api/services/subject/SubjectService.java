package com.opitral.ads.market.api.services.subject;

import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.opitral.ads.market.api.criteria.Criteria;
import com.opitral.ads.market.api.criteria.SubjectCriteria;
import com.opitral.ads.market.api.domain.entity.SubjectEntity;
import com.opitral.ads.market.api.exception.BaseException;
import com.opitral.ads.market.api.exception.NoSuchEntityException;
import com.opitral.ads.market.api.model.response.SubjectListResponse;
import com.opitral.ads.market.api.model.response.SubjectResponse;
import com.opitral.ads.market.api.model.view.SubjectView;
import com.opitral.ads.market.api.services.BaseService;

@Service
@Slf4j
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BaseException.class)
public class SubjectService extends BaseService<SubjectEntity, SubjectView> {

    public SubjectService() {
        super(SubjectEntity.class, SubjectEntity::new);
    }

    @Override
    public Criteria<SubjectEntity> parse(String restrict) {
        return new SubjectCriteria(restrict);
    }

    @Override
    public boolean delete(Integer id) {
        SubjectEntity entity = repository.findById(id).orElseThrow(
                () -> new NoSuchEntityException(SubjectEntity.class.getName(), "by id: " + id)
        );
        validationService.validForDelete(entity);
        repository.delete(entity);
        log.debug("Deleted subject: {}", entity);

        return true;
    }

    public SubjectResponse getSubjectById(Integer id) {
        return buildSubjectResponseDto(getById(id));
    }

    public SubjectListResponse getAllSubjects(String restrict) {
        List<SubjectResponse> responseList = getList(parse(restrict)).stream()
                .map(this::buildSubjectResponseDto)
                .collect(Collectors.toList());

        return new SubjectListResponse(count(restrict), responseList);
    }

    public SubjectResponse buildSubjectResponseDto(SubjectEntity entity) {
        return SubjectResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}