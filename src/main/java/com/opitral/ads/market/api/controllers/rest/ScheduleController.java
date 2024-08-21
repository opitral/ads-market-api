package com.opitral.ads.market.api.controllers.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opitral.ads.market.api.common.response.Response;
import com.opitral.ads.market.api.model.response.ScheduleResponse;
import com.opitral.ads.market.api.services.schedule.ScheduleService;

@Slf4j
@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/{id}")
    public ResponseEntity<Response<ScheduleResponse>> getSchedule(@PathVariable Integer id) {
        return ResponseEntity.ok(Response.of(scheduleService.getSchedule(id)));
    }
}
