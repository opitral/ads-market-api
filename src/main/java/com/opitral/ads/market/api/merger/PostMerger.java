package com.opitral.ads.market.api.merger;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import com.opitral.ads.market.api.domain.entity.Button;
import com.opitral.ads.market.api.domain.entity.GroupEntity;
import com.opitral.ads.market.api.domain.entity.PostEntity;
import com.opitral.ads.market.api.domain.entity.Publication;
import com.opitral.ads.market.api.exception.NoSuchEntityException;
import com.opitral.ads.market.api.model.view.PostView;
import com.opitral.ads.market.api.repositories.GroupRepository;

@Component
@RequiredArgsConstructor
public class PostMerger implements Merger<PostEntity, PostView> {

    private final GroupRepository groupRepository;

    @Override
    public void mergeCreate(PostEntity entity, PostView view) {
        mergeMainFields(entity, view);
    }

    @Override
    public void mergeEdit(PostEntity entity, PostView view) {
        if (view.getId() != null)
            entity.setId(view.getId());

        mergeMainFields(entity, view);
    }

    private void mergeMainFields(PostEntity entity, PostView view) {
        if (view.getPublication() != null) {
            Button button = view.getPublication().getButton() != null ? new Button(
                    view.getPublication().getButton().getName(),
                    view.getPublication().getButton().getUrl()
            ) : null;
            Button button2 = view.getPublication().getButton_2() != null ? new Button(
                    view.getPublication().getButton_2().getName(),
                    view.getPublication().getButton_2().getUrl()
            ) : null;
            Button button3 = view.getPublication().getButton_3() != null ? new Button(
                    view.getPublication().getButton_3().getName(),
                    view.getPublication().getButton_3().getUrl()
            ) : null;
            Button button4 = view.getPublication().getButton_4() != null ? new Button(
                    view.getPublication().getButton_4().getName(),
                    view.getPublication().getButton_4().getUrl()
            ) : null;
            Button button5 = view.getPublication().getButton_5() != null ? new Button(
                    view.getPublication().getButton_5().getName(),
                    view.getPublication().getButton_5().getUrl()
            ) : null;

            entity.setPublication(new Publication(
                    view.getPublication().getType(),
                    view.getPublication().getText(),
                    view.getPublication().getFileId(),
                    view.getPublication().getFileId_2(),
                    view.getPublication().getFileId_3(),
                    view.getPublication().getFileId_4(),
                    view.getPublication().getFileId_5(),
                    view.getPublication().getFileId_6(),
                    view.getPublication().getFileId_7(),
                    view.getPublication().getFileId_8(),
                    view.getPublication().getFileId_9(),
                    view.getPublication().getFileId_10(),
                    button,
                    button2,
                    button3,
                    button4,
                    button5
            ));
        }

        if (view.getGroupId() != null) {
            entity.setGroup(groupRepository.findById(view.getGroupId()).orElseThrow(
                    () -> new NoSuchEntityException(GroupEntity.class.getName(), "by id: " + view.getGroupId())
            ));
            entity.setGroupTelegramId(entity.getGroup().getGroupTelegramId());
        }

        if (view.getWithPin() != null) {
            entity.setWithPin(view.getWithPin());
        }

        if (view.getPublishDate() != null) {
            entity.setPublishDate(view.getPublishDate());
        }

        if (view.getPublishTime() != null) {
            entity.setPublishTime(view.getPublishTime().withSecond(0));
        }

        if (view.getStatus() != null) {
            entity.setStatus(view.getStatus());
        }

        if (view.getMessageId() != null) {
            entity.setMessageId(view.getMessageId());
        }
    }
}
