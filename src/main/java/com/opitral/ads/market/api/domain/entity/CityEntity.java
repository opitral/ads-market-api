package com.opitral.ads.market.api.domain.entity;

import java.io.Serializable;
import java.time.Instant;

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
@Table(name = "city")
public class CityEntity implements Serializable, GettableById {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "error.city.name.empty")
    @Size(max = 250, message = "error.city.name.size")
    private String nameUa;

    @NotNull(message = "error.city.name.empty")
    @Size(max = 250, message = "error.city.name.size")
    private String nameRu;

    @NotNull(message = "error.city.name.empty")
    @Size(max = 250, message = "error.city.name.size")
    private String nameEn;

    @Column(name = "subject_id", insertable = false, updatable = false)
    private Integer subjectId;

    @NotNull(message = "error.city.subject.empty")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private SubjectEntity subject;

    @Builder.Default
    private final Instant createdAt = Instant.now();

    @Builder.Default
    private Instant updatedAt = Instant.now();
}
