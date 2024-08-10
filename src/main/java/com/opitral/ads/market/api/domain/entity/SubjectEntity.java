package com.opitral.ads.market.api.domain.entity;

import java.io.Serializable;
import java.time.Instant;

import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

import com.opitral.ads.market.api.common.helpers.GettableById;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subject")
public class SubjectEntity implements Serializable, GettableById {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    @NotNull(message = "error.subject.name.empty")
    @Size(max = 250, message = "error.subject.name.size")
    private String nameUa;

    @Column(unique = true)
    @NotNull(message = "error.subject.name.empty")
    @Size(max = 250, message = "error.subject.name.size")
    private String nameRu;

    @Column(unique = true)
    @NotNull(message = "error.subject.name.empty")
    @Size(max = 250, message = "error.subject.name.size")
    private String nameEn;

    @Builder.Default
    private Instant createdAt = Instant.now();

    @Builder.Default
    private Instant updatedAt = Instant.now();
}
