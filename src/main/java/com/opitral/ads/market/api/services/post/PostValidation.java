package com.opitral.ads.market.api.services.post;

import org.springframework.stereotype.Service;

import com.opitral.ads.market.api.domain.entity.PostEntity;
import com.opitral.ads.market.api.services.BaseValidator;

@Service
public class PostValidation extends BaseValidator<PostEntity> {

    public PostValidation() {
        super(PostEntity.class);
    }

    @Override
    public void validForCreate(PostEntity entity) {
        super.validForCreate(entity);
    }

    @Override
    public void validForUpdate(PostEntity entity) {
        super.validForUpdate(entity);
    }

    @Override
    public void validForDelete(PostEntity entity) {
        super.validForDelete(entity);
    }

}
