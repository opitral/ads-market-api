package com.opitral.ads.market.api.services.group;

import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.opitral.ads.market.api.criteria.Criteria;
import com.opitral.ads.market.api.criteria.GroupCriteria;
import com.opitral.ads.market.api.domain.entity.CityEntity;
import com.opitral.ads.market.api.domain.entity.GroupEntity;
import com.opitral.ads.market.api.exception.BaseException;
import com.opitral.ads.market.api.exception.NoSuchEntityException;
import com.opitral.ads.market.api.model.response.*;
import com.opitral.ads.market.api.model.view.GroupView;
import com.opitral.ads.market.api.services.BaseService;
import com.opitral.ads.market.api.domain.entity.Price;

@Service
@Slf4j
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BaseException.class)
public class GroupService extends BaseService<GroupEntity, GroupView> {

    public GroupService() {
        super(GroupEntity.class, GroupEntity::new);
    }

    @Override
    public Criteria<GroupEntity> parse(String restrict) {
        return new GroupCriteria(restrict);
    }

    @Override
    public boolean delete(Integer id) {
        GroupEntity entity = repository.findById(id).orElseThrow(
                () -> new NoSuchEntityException(CityEntity.class.getName(), "by id: " + id)
        );
        validationService.validForDelete(entity);
        repository.delete(entity);
        log.debug("Deleted group: {}", entity);

        return true;
    }

    public GroupResponse getGroupById(Integer id) {
        return buildGroupResponseDto(getById(id));
    }

    public GroupListResponse getAllGroups(String restrict) {
        List<GroupResponse> responseList = getList(parse(restrict)).stream()
                .map(this::buildGroupResponseDto)
                .collect(Collectors.toList());

        return new GroupListResponse(count(restrict), responseList);
    }

    public GroupResponse buildGroupResponseDto(GroupEntity entity) {
        return GroupResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .link(entity.getLink())
                .groupTelegramId(entity.getGroupTelegramId())
                .userTelegramId(entity.getUserTelegramId())
                .cityId(entity.getCityId())
                .workingHoursStart(entity.getWorkingHoursStart())
                .workingHoursEnd(entity.getWorkingHoursEnd())
                .postIntervalInMinutes(entity.getPostIntervalInMinutes())
                .priceForOneDay(buildPriceResponseDto(entity.getPriceForOneDay()))
                .priceForOneWeek(buildPriceResponseDto(entity.getPriceForOneWeek()))
                .priceForTwoWeeks(buildPriceResponseDto(entity.getPriceForTwoWeeks()))
                .priceForOneMonth(buildPriceResponseDto(entity.getPriceForOneMonth()))
                .averagePostViews(entity.getAveragePostViews())
                .build();
    }

    public PriceResponse buildPriceResponseDto(Price obj) {
        return PriceResponse.builder()
                .withoutPin(obj.getWithoutPin())
                .withPin(obj.getWithPin())
                .build();
    }
}
