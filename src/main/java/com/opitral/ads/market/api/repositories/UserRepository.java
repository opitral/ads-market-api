package com.opitral.ads.market.api.repositories;

import java.util.Optional;

import com.opitral.ads.market.api.domain.entity.UserEntity;


public interface UserRepository extends BaseRepository<UserEntity> {
    Optional<UserEntity> findByTelegramId(String telegramId);
}
