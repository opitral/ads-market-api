package com.opitral.ads.market.api.services.post;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.opitral.ads.market.api.criteria.Criteria;
import com.opitral.ads.market.api.criteria.PostCriteria;
import com.opitral.ads.market.api.domain.entity.*;
import com.opitral.ads.market.api.domain.entity.PostEntity;
import com.opitral.ads.market.api.exception.BaseException;
import com.opitral.ads.market.api.exception.NoSuchEntityException;
import com.opitral.ads.market.api.model.response.*;
import com.opitral.ads.market.api.model.view.PostView;
import com.opitral.ads.market.api.services.BaseService;

@Service
@Slf4j
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BaseException.class)
public class PostService extends BaseService<PostEntity, PostView> {

    public PostService() {
        super(PostEntity.class, PostEntity::new);
    }

    @Override
    public Criteria<PostEntity> parse(String restrict) {
        return new PostCriteria(restrict);
    }

    @Override
    public boolean delete(Integer id) {
        PostEntity entity = repository.findById(id).orElseThrow(
                () -> new NoSuchEntityException(CityEntity.class.getName(), "by id: " + id)
        );
        validationService.validForDelete(entity);
        repository.delete(entity);
        log.debug("Deleted post: {}", entity);

        return true;
    }

    public PostResponse getPostById(Integer id) {
        return buildPostResponseDto(getById(id));
    }

    public PostListResponse getAllPosts(String restrict) {
        List<PostResponse> responseList;

        responseList = getList(parse(restrict)).stream()
                .filter(this::existPostFromToday)
                .map(this::buildPostResponseDto)
                .collect(Collectors.toList());

        return new PostListResponse((long) responseList.size(), responseList);
    }

    public PostResponse buildPostResponseDto(PostEntity entity) {
        return PostResponse.builder()
                .id(entity.getId())
                .groupId(entity.getGroup().getId())
                .groupTelegramId(entity.getGroupTelegramId())
                .withPin(entity.getWithPin())
                .publication(buildPublicationResponseDto(entity.getPublication()))
                .publishDate(entity.getPublishDate())
                .publishTime(entity.getPublishTime())
                .status(entity.getStatus())
                .messageId(entity.getMessageId())
                .build();
    }

    public PublicationResponse buildPublicationResponseDto(Publication obj) {
        return PublicationResponse.builder()
                .type(obj.getType())
                .fileId(obj.getFileId())
                .text(obj.getText())
                .button(
                        obj.getButton() != null ? new ButtonResponse(
                                obj.getButton().getName(),
                                obj.getButton().getUrl()
                        ) : null
                )
                .build();
    }

    public Boolean existPostFromToday(PostEntity entity) {
        LocalDate today = LocalDate.now();
        return !entity.getPublishDate().isBefore(today);
    }
}
