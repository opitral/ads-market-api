package com.opitral.ads.market.api.services.user;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.opitral.ads.market.api.domain.entity.UserEntity;
import com.opitral.ads.market.api.exception.ValidationException;
import com.opitral.ads.market.api.repositories.UserRepository;
import com.opitral.ads.market.api.services.BaseValidator;

@Service
public class UserValidation extends BaseValidator<UserEntity> {

    private final UserRepository userRepository;

    public UserValidation(UserRepository userRepository) {
        super(UserEntity.class);
        this.userRepository = userRepository;
    }

    @Override
    public void validForCreate(UserEntity entity) {
        super.validForCreate(entity);

        Optional<UserEntity> foundUser = userRepository.findByTelegramId(entity.getTelegramId());

        if (foundUser.isPresent()) {
            throw new ValidationException(UserEntity.class.getName(), "error.TelegramIdExistsException");
        }
    }

    @Override
    public void validForUpdate(UserEntity entity) {
        super.validForUpdate(entity);
    }

    @Override
    public void validForDelete(UserEntity entity) {
        super.validForDelete(entity);
    }
}
