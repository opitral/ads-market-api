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

    @NotNull(message = "error.group.name.empty")
    @Size(max = 250, message = "error.group.name.size")
    private String name;

    @Size(max = 250, message = "error.group.link.size")
    private String link;

    @NotNull(message = "error.group.telegram.id.empty")
    private String groupTelegramId;

    @NotNull(message = "error.group.user.telegram.id.empty")
    private String userTelegramId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "city_id", insertable = false, updatable = false)
    private Integer cityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private CityEntity city;

    @NotNull(message = "error.group.working.hours.empty")
    private LocalTime workingHoursStart;

    @NotNull(message = "error.group.working.hours.empty")
    private LocalTime workingHoursEnd;

    @NotNull(message = "error.group.post.interval.empty")
    @Min(value = 30, message = "error.group.post.interval.size")
    @Max(value = 720, message = "error.group.post.interval.size")
    private Integer postIntervalInMinutes;

    @Embedded
    @NotNull(message = "error.group.price.for.one.day.empty")
    @AttributeOverrides({
            @AttributeOverride(name = "withPin", column = @Column(name = "one_day_with_pin")),
            @AttributeOverride(name = "withoutPin", column = @Column(name = "one_day_without_pin"))
    })
    @Valid
    private Price priceForOneDay;

    @Embedded
    @NotNull(message = "error.group.price.empty")
    @AttributeOverrides({
            @AttributeOverride(name = "withPin", column = @Column(name = "one_week_with_pin")),
            @AttributeOverride(name = "withoutPin", column = @Column(name = "one_week_without_pin"))
    })
    @Valid
    private Price priceForOneWeek;

    @Embedded
    @NotNull(message = "error.group.price.empty")
    @AttributeOverrides({
            @AttributeOverride(name = "withPin", column = @Column(name = "two_weeks_with_pin")),
            @AttributeOverride(name = "withoutPin", column = @Column(name = "two_weeks_without_pin"))
    })
    @Valid
    private Price priceForTwoWeeks;

    @Embedded
    @NotNull(message = "error.group.price.empty")
    @AttributeOverrides({
            @AttributeOverride(name = "withPin", column = @Column(name = "one_month_with_pin")),
            @AttributeOverride(name = "withoutPin", column = @Column(name = "one_month_without_pin"))
    })
    @Valid
    private Price priceForOneMonth;

    private Integer averagePostViews;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostEntity> posts = new ArrayList<>();

    @Builder.Default
    private final Instant createdAt = Instant.now();

}