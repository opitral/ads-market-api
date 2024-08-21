package com.opitral.ads.market.api.model.view;

import java.io.Serializable;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.opitral.ads.market.api.common.helpers.GettableById;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupView implements Serializable, GettableById {
    private Integer id;

    private String name;
    private String link;
    private String groupTelegramId;

    private String userTelegramId;
    private Integer cityId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH")
    private LocalTime workingHoursStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH")
    private LocalTime workingHoursEnd;
    private Integer postIntervalInMinutes;

    private PriceView priceForOneDay;
    private PriceView priceForOneWeek;
    private PriceView priceForTwoWeeks;
    private PriceView priceForOneMonth;

    private Integer averagePostViews;
}
