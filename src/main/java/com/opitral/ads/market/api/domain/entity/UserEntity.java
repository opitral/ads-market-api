package com.opitral.ads.market.api.domain.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.opitral.ads.market.api.common.helpers.GettableById;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity implements Serializable, GettableById {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Telegram ID пользователя не может быть пустым")
    private String telegramId;

    private String firstName;

    private String lastName;

    private String username;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupEntity> groups;

    @NotNull(message = "Количество разрешенных групп не должно быть пустым")
    private Integer allowedGroupsCount;

    @Builder.Default
    private final Instant createdAt = Instant.now();

}
