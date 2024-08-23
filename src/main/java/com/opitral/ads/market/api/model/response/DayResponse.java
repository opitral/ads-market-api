package com.opitral.ads.market.api.model.response;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class DayResponse {
    private LocalDate date;
    private List<TimeResponse> times;
}
