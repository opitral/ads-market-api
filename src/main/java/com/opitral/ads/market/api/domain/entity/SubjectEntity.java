package com.opitral.ads.market.api.domain.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
@Table(name = "subjects")
public class SubjectEntity implements Serializable, GettableById {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Название направления не может быть пустым")
    @Size(max = 250, message = "Название напрвления должно содержать не более 250 символов")
    private String name;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<CityEntity> cities = new ArrayList<>();

}
