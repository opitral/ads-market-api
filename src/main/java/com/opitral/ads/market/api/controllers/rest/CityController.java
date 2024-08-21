package com.opitral.ads.market.api.controllers.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.opitral.ads.market.api.common.response.CommonResponse;
import com.opitral.ads.market.api.common.response.Response;
import com.opitral.ads.market.api.model.response.CityListResponse;
import com.opitral.ads.market.api.model.response.CityResponse;
import com.opitral.ads.market.api.model.view.CityView;
import com.opitral.ads.market.api.services.city.CityService;

@Slf4j
@RestController
@RequestMapping("/api/city")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @PostMapping
    public ResponseEntity<CommonResponse.IntegerResponse> createCity(@Valid @RequestBody CityView body) {
        return ResponseEntity.ok(CommonResponse.IntegerResponse.of(cityService.create(body)));
    }

    @PutMapping
    public ResponseEntity<CommonResponse.BooleanResponse> updateCity(@Valid @RequestBody CityView body) {
        return ResponseEntity.ok(CommonResponse.BooleanResponse.of(cityService.update(body)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse.BooleanResponse> deleteCityById(@PathVariable Integer id) {
        return ResponseEntity.ok(CommonResponse.BooleanResponse.of(cityService.delete(id)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<CityResponse>> getCityById(@PathVariable Integer id) {
        return ResponseEntity.ok(Response.of(cityService.getCityById(id)));
    }

    @GetMapping
    public ResponseEntity<Response<CityListResponse>> getListOfCities(@Valid @RequestParam(required = false) String restrict) {
        return ResponseEntity.ok(Response.of(cityService.getAllCities(restrict)));
    }

}