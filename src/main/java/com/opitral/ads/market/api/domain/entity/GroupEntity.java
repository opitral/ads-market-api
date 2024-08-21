package com.opitral.ads.market.api.domain.entity;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import lombok.*;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
@Table(name = "groups")
public class GroupEntity implements Serializable, GettableById {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Название группы не может быть пустым")
    @Size(max = 250, message = "Название группы должно содержать не более 250 символов")
    private String name;

    @Size(max = 250, message = "Ссылка на группу должна содержать не более 250 символов")
    private String link;

    @NotNull(message = "Telegram ID группы не может быть пустым")
    private String groupTelegramId;

    @NotNull(message = "Telegram ID пользователя не может быть пустым")
    private String userTelegramId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "city_id", insertable = false, updatable = false)
    private Integer cityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private CityEntity city;

    @NotNull(message = "Время начала роботы не может быть пустым")
    private LocalTime workingHoursStart;

    @NotNull(message = "Время звершения роботы не может быть пустым")
    private LocalTime workingHoursEnd;

    @NotNull(message = "Интервал между постами не должен быть пустым")
    @Min(value = 30, message = "Минимальный интервал между постами - 30 минут")
    @Max(value = 720, message = "Максимальный интервал между постами - 720 минут")
    private Integer postIntervalInMinutes;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "withPin", column = @Column(name = "one_day_with_pin")),
            @AttributeOverride(name = "withoutPin", column = @Column(name = "one_day_without_pin"))
    })
    @Valid
    private Price priceForOneDay;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "withPin", column = @Column(name = "one_week_with_pin")),
            @AttributeOverride(name = "withoutPin", column = @Column(name = "one_week_without_pin"))
    })
    @Valid
    private Price priceForOneWeek;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "withPin", column = @Column(name = "two_weeks_with_pin")),
            @AttributeOverride(name = "withoutPin", column = @Column(name = "two_weeks_without_pin"))
    })
    @Valid
    private Price priceForTwoWeeks;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "withPin", column = @Column(name = "one_month_with_pin")),
            @AttributeOverride(name = "withoutPin", column = @Column(name = "one_month_without_pin"))
    })
    @Valid
    private Price priceForOneMonth;

    private Integer averagePostViews;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<PostEntity> posts = new ArrayList<>();

}