package com.opitral.ads.market.api.services.group;

import org.springframework.stereotype.Service;

import com.opitral.ads.market.api.domain.entity.GroupEntity;
import com.opitral.ads.market.api.exception.ValidationException;
import com.opitral.ads.market.api.services.BaseValidator;

import java.time.LocalTime;

@Service
public class GroupValidation extends BaseValidator<GroupEntity> {

    public GroupValidation() {
        super(GroupEntity.class);
    }

    @Override
    public void validForCreate(GroupEntity entity) {
        super.validForCreate(entity);

        if (entity.getWorkingHoursStart().isAfter(entity.getWorkingHoursEnd())  && !entity.getWorkingHoursEnd().equals(LocalTime.MIDNIGHT))
            throw new ValidationException(GroupEntity.class.getName(), "Время начала роботы не должно быть позже завершения работы");

        if (entity.getPostIntervalInMinutes() % 30 != 0) {
            throw new ValidationException(GroupEntity.class.getName(), "Интервал между публикациями постов должен быть кратным 30");
        }
    }

    @Override
    public void validForUpdate(GroupEntity entity) {
        super.validForUpdate(entity);

        if (entity.getWorkingHoursStart().isAfter(entity.getWorkingHoursEnd()) && !entity.getWorkingHoursEnd().equals(LocalTime.MIDNIGHT))
            throw new ValidationException(GroupEntity.class.getName(), "Время начала роботы не должно быть позже завершения работы");

        if (entity.getPostIntervalInMinutes() % 30 != 0) {
            throw new ValidationException(GroupEntity.class.getName(), "Интервал между публикациями постов должен быть кратным 30");
        }
    }

    @Override
    public void validForDelete(GroupEntity entity) {
        super.validForDelete(entity);
    }
}
