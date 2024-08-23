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
    private Publication publication;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private GroupEntity group;

    @NotNull(message = "Telegram ID группы не может быть пустым")
    private String groupTelegramId;

    @NotNull(message = "Дата публикации поста не может быть пустой")
    private LocalDate publishDate;

    @NotNull(message = "Время публикации поста не может быть пустым")
    private LocalTime publishTime;

    @NotNull(message = "Статус поста не может быть пустым")
    @Enumerated(EnumType.STRING)
    private PostStatus status;

    private Integer messageId;

}
