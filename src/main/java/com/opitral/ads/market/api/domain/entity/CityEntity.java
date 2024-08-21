package com.opitral.ads.market.api.domain.entity;

import java.io.Serializable;
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
@Table(name = "cities")
public class CityEntity implements Serializable, GettableById {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Название города не должно быть пустым")
    @Size(max = 250, message = "Название города должно содержать не более 50 символов")
    private String name;

    @Column(name = "subject_id", insertable = false, updatable = false)
    private Integer subjectId;

    @NotNull(message = "Напрвление не должно быть пустым")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private SubjectEntity subject;

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<GroupEntity> groups;

}
