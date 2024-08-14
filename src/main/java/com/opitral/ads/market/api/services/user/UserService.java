package com.opitral.ads.market.api.services.user;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.opitral.ads.market.api.criteria.Criteria;
import com.opitral.ads.market.api.criteria.UserCriteria;
import com.opitral.ads.market.api.domain.entity.UserEntity;
import com.opitral.ads.market.api.exception.BaseException;
import com.opitral.ads.market.api.exception.NoSuchEntityException;
import com.opitral.ads.market.api.model.response.UserListResponse;
import com.opitral.ads.market.api.model.response.UserResponse;
import com.opitral.ads.market.api.model.view.UserView;
import com.opitral.ads.market.api.repositories.UserRepository;
import com.opitral.ads.market.api.services.BaseService;

@Service
@Slf4j
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BaseException.class)
public class UserService extends BaseService<UserEntity, UserView> {

    public UserService() {
        super(UserEntity.class, UserEntity::new);
    }

    @Override
    public Criteria<UserEntity> parse(String restrict) {
        return new UserCriteria(restrict);
    }

    @Override
    public boolean delete(Integer id) {
        UserEntity entity = repository.findById(id).orElseThrow(
                () -> new NoSuchEntityException(UserEntity.class.getName(), "by id: " + id)
        );
        validationService.validForDelete(entity);
        repository.delete(entity);
        log.debug("Deleted user: {}", entity);

        return true;
    }

    @Override
    @Transactional
    public void updateEntity(UserEntity entity) {
        entity.setUpdatedAt(Instant.now());
        repository.saveAndFlush(entity);
    }

    public UserResponse getUserByTelegramId(String telegramId) {
        UserEntity entity = ((UserRepository) repository).findByTelegramId(telegramId).orElseThrow(
                () -> new NoSuchEntityException(UserEntity.class.getName(), "by telegram id: " + telegramId)
        );

        return buildUserResponseDto(entity);
    }

    public UserResponse getUserById(Integer id) {
        return buildUserResponseDto(getById(id));
    }

    public UserListResponse getAllUsers(String restrict) {
        List<UserResponse> responseList = getList(parse(restrict)).stream()
                .map(this::buildUserResponseDto)
                .collect(Collectors.toList());

        return new UserListResponse(count(restrict), responseList);
    }

    public UserResponse buildUserResponseDto(UserEntity entity) {
        return UserResponse.builder()
                .id(entity.getId())
                .telegramId(entity.getTelegramId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .build();
    }
}
