package com.opitral.ads.market.api.merger;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import com.opitral.ads.market.api.domain.entity.UserEntity;
import com.opitral.ads.market.api.model.view.UserView;

@Component
@RequiredArgsConstructor
public class UserMerger implements Merger<UserEntity, UserView> {

    @Override
    public void mergeCreate(UserEntity entity, UserView view) {
        mergeMainFields(entity, view);
    }

    @Override
    public void mergeEdit(UserEntity entity, UserView view) {
        mergeMainFields(entity, view);
    }

    private void mergeMainFields(UserEntity entity, UserView view) {
        if (view.getId() != null)
            entity.setId(view.getId());

        if (view.getTelegramId() != null)
            entity.setTelegramId(view.getTelegramId());

        if (view.getFirstName() != null)
            entity.setFirstName(view.getFirstName());

        if (view.getLastName() != null)
            entity.setLastName(view.getLastName());
    }
}
