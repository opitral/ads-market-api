package com.opitral.ads.market.api.model.response;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import com.opitral.ads.market.api.domain.enums.DateTimeStatus;

@AllArgsConstructor
@Builder
@Data
public class ScheduleDateTimeResponse {
    private LocalDate date;
    private LocalTime time;
    private DateTimeStatus status;
}
