package com.opitral.ads.market.api.services.post;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Service;

import com.opitral.ads.market.api.domain.entity.PostEntity;
import com.opitral.ads.market.api.exception.ValidationException;
import com.opitral.ads.market.api.repositories.PostRepository;
import com.opitral.ads.market.api.services.BaseValidator;

@Service
public class PostValidation extends BaseValidator<PostEntity> {

    private final PostRepository postRepository;

    public PostValidation(PostRepository postRepository) {
        super(PostEntity.class);
        this.postRepository = postRepository;
    }

    @Override
    public void validForCreate(PostEntity entity) {
        super.validForCreate(entity);

        if (entity.getPublishDate().isBefore(LocalDate.now())) {
            throw new ValidationException(PostEntity.class.getName(), "Дата находится в прошлом");
        }

        if (entity.getPublishTime().isBefore(LocalTime.now())) {
            throw new ValidationException(PostEntity.class.getName(), "Время находится в прошлом");
        }

        if (postRepository.existsByGroupIdAndPublishDateAndPublishTime(entity.getGroup().getId(), entity.getPublishDate(), entity.getPublishTime())) {
            throw new ValidationException(PostEntity.class.getName(), "Дата и время заняты");
        }
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
