package com.opitral.ads.market.api.model.response;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonFormat;

@AllArgsConstructor
@Builder
@Data
public class GroupResponse {
    private Integer id;

    private String name;
    private String link;
    private String groupTelegramId;

    private UserResponse user;
    private CityResponse city;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime workingHoursStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime workingHoursEnd;
    private Integer postIntervalInMinutes;

    private PriceResponse priceForOneDay;
    private PriceResponse priceForOneWeek;
    private PriceResponse priceForTwoWeeks;
    private PriceResponse priceForOneMonth;

    private Integer averagePostViews;
}
