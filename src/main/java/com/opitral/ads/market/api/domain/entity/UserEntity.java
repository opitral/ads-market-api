package com.opitral.ads.market.api.domain.entity;

import com.opitral.ads.market.api.common.helpers.GettableById;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

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

    @NotNull(message = "error.user.telegram.id.empty")
    @Size(min = 9, max = 12, message = "error.user.telegram.id.size")
    private String telegramId;

    @Size(max = 250, message = "error.first.name.size")
    private String firstName;

    @Size(max = 250, message = "error.last.name.size")
    private String lastName;

    @Builder.Default
    private final Instant createdAt = Instant.now();

    @Builder.Default
    private Instant updatedAt = Instant.now();
}
