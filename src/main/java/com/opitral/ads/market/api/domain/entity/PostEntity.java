package com.opitral.ads.market.api.domain.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import com.opitral.ads.market.api.common.helpers.GettableById;
import com.opitral.ads.market.api.domain.enums.PostStatus;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class PostEntity implements Serializable, GettableById {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Valid
    @AttributeOverrides({
            @AttributeOverride(name = "type", column = @Column(name = "type", nullable = false)),
            @AttributeOverride(name = "text", column = @Column(name = "text", length = 4096)),
            @AttributeOverride(name = "fileId", column = @Column(name = "file_id")),
            @AttributeOverride(name = "fileId_2", column = @Column(name = "file_id_2")),
            @AttributeOverride(name = "fileId_3", column = @Column(name = "file_id_3")),
            @AttributeOverride(name = "fileId_4", column = @Column(name = "file_id_4")),
            @AttributeOverride(name = "fileId_5", column = @Column(name = "file_id_5")),
            @AttributeOverride(name = "fileId_6", column = @Column(name = "file_id_6")),
            @AttributeOverride(name = "fileId_7", column = @Column(name = "file_id_7")),
            @AttributeOverride(name = "fileId_8", column = @Column(name = "file_id_8")),
            @AttributeOverride(name = "fileId_9", column = @Column(name = "file_id_9")),
            @AttributeOverride(name = "fileId_10", column = @Column(name = "file_id_10")),
            @AttributeOverride(name = "button.name", column = @Column(name = "name", length = 50)),
            @AttributeOverride(name = "button.url", column = @Column(name = "url")),
            @AttributeOverride(name = "button_2.name", column = @Column(name = "name_2", length = 50)),
            @AttributeOverride(name = "button_2.url", column = @Column(name = "url_2")),
            @AttributeOverride(name = "button_3.name", column = @Column(name = "name_3", length = 50)),
            @AttributeOverride(name = "button_3.url", column = @Column(name = "url_3")),
            @AttributeOverride(name = "button_4.name", column = @Column(name = "name_4", length = 50)),
            @AttributeOverride(name = "button_4.url", column = @Column(name = "url_4")),
            @AttributeOverride(name = "button_5.name", column = @Column(name = "name_5", length = 50)),
            @AttributeOverride(name = "button_5.url", column = @Column(name = "url_5"))
    })
    private Publication publication;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private GroupEntity group;

    @NotNull(message = "Telegram ID группы не может быть пустым")
    private String groupTelegramId;

    @NotNull(message = "Флаг закрепленного сообщения не может быть пустым")
    private Boolean withPin;

    @NotNull(message = "Дата публикации поста не может быть пустой")
    private LocalDate publishDate;

    @NotNull(message = "Время публикации поста не может быть пустым")
    private LocalTime publishTime;

    @NotNull(message = "Статус поста не может быть пустым")
    @Enumerated(EnumType.STRING)
    private PostStatus status;

    private Integer messageId;

}
