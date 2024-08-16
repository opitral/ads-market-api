package com.opitral.ads.market.api.services.group;

import org.springframework.stereotype.Service;

import com.opitral.ads.market.api.domain.entity.GroupEntity;
import com.opitral.ads.market.api.exception.ValidationException;
import com.opitral.ads.market.api.services.BaseValidator;

@Service
public class GroupValidation extends BaseValidator<GroupEntity> {

    public GroupValidation() {
        super(GroupEntity.class);
    }

    @Override
    public void validForCreate(GroupEntity entity) {
        super.validForCreate(entity);

        if (entity.getUser().getGroups().size() >= entity.getUser().getAllowedGroupsCount())
            throw new ValidationException(GroupEntity.class.getName(), "error.group.limit.out");

        if (entity.getWorkingHoursStart().isAfter(entity.getWorkingHoursEnd()))
            throw new ValidationException(GroupEntity.class.getName(), "error.group.working.hours.start");

        if (entity.getPostIntervalInMinutes() % 30 != 0) {
            throw new ValidationException(GroupEntity.class.getName(), "error.group.post.interval.value");
        }
    }

    @Override
    public void validForUpdate(GroupEntity entity) {
        super.validForUpdate(entity);

        if (entity.getPostIntervalInMinutes() % 30 != 0) {
            throw new ValidationException(GroupEntity.class.getName(), "error.group.post.interval.value");
        }
    }

    @Override
    public void validForDelete(GroupEntity entity) {
        super.validForDelete(entity);
    }
}
