package com.opitral.ads.market.api.model.response;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import com.opitral.ads.market.api.domain.enums.ScheduleStatus;

@AllArgsConstructor
@Builder
@Data
public class TimeResponse {
    private LocalTime time;
    private ScheduleStatus status;
}
