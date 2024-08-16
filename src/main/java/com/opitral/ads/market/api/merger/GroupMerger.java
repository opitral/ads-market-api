package com.opitral.ads.market.api.merger;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import com.opitral.ads.market.api.domain.entity.CityEntity;
import com.opitral.ads.market.api.domain.entity.GroupEntity;
import com.opitral.ads.market.api.domain.entity.UserEntity;
import com.opitral.ads.market.api.exception.NoSuchEntityException;
import com.opitral.ads.market.api.model.view.GroupView;
import com.opitral.ads.market.api.repositories.CityRepository;
import com.opitral.ads.market.api.repositories.UserRepository;
import com.opitral.ads.market.api.domain.entity.Price;

@Component
@RequiredArgsConstructor
public class GroupMerger implements Merger<GroupEntity, GroupView>  {

    private final UserRepository userRepository;
    private final CityRepository cityRepository;

    @Override
    public void mergeCreate(GroupEntity entity, GroupView view) {
        mergeMainFields(entity, view);
    }

    @Override
    public void mergeEdit(GroupEntity entity, GroupView view) {
        if (view.getId() != null)
            entity.setId(view.getId());

        mergeMainFields(entity, view);
    }

    private void mergeMainFields(GroupEntity entity, GroupView view) {
        if (view.getName() != null)
            entity.setName(view.getName());

        if (view.getLink() != null)
            entity.setLink(view.getLink());

        if (view.getGroupTelegramId() != null)
            entity.setGroupTelegramId(view.getGroupTelegramId());

        if (view.getUserTelegramId() != null) {
            entity.setUser(userRepository.findByTelegramId(view.getUserTelegramId()).orElseThrow(
                    () -> new NoSuchEntityException(UserEntity.class.getName(), "by telegram id: " + view.getUserTelegramId())
            ));
            entity.setUserTelegramId(view.getUserTelegramId());
        }

        if (view.getCityId() != null) {
            entity.setCity(cityRepository.findById(view.getCityId()).orElseThrow(
                    () -> new NoSuchEntityException(CityEntity.class.getName(), "by id: " + view.getCityId())
            ));
            entity.setCityId(view.getCityId());
        }

        if (view.getWorkingHoursStart() != null)
            entity.setWorkingHoursStart(view.getWorkingHoursStart());

        if (view.getWorkingHoursEnd() != null)
            entity.setWorkingHoursEnd(view.getWorkingHoursEnd());

        if (view.getPostIntervalInMinutes() != null)
            entity.setPostIntervalInMinutes(view.getPostIntervalInMinutes());

        if (view.getPriceForOneDay() != null)
            entity.setPriceForOneDay(new Price(
                    view.getPriceForOneDay().getWithoutPin(), view.getPriceForOneDay().getWithPin()
            ));

        if (view.getPriceForOneWeek() != null)
            entity.setPriceForOneWeek(new Price(
                    view.getPriceForOneWeek().getWithoutPin(), view.getPriceForOneWeek().getWithPin()
            ));

        if (view.getPriceForTwoWeeks() != null)
            entity.setPriceForTwoWeeks(new Price(
                    view.getPriceForTwoWeeks().getWithoutPin(), view.getPriceForTwoWeeks().getWithPin()
            ));

        if (view.getPriceForOneMonth() != null)
            entity.setPriceForOneMonth(new Price(
                    view.getPriceForOneMonth().getWithoutPin(), view.getPriceForOneMonth().getWithPin()
            ));

        if (view.getAveragePostViews() != null)
            entity.setAveragePostViews(view.getAveragePostViews());
    }
}
