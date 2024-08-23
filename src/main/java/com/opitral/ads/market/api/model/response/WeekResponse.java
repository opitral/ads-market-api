package com.opitral.ads.market.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class WeekResponse {
    private List<DayResponse> days;
}
