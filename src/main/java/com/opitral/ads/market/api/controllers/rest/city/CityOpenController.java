package com.opitral.ads.market.api.controllers.rest.city;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.opitral.ads.market.api.common.response.CommonResponse;
import com.opitral.ads.market.api.common.response.Response;
import com.opitral.ads.market.api.model.response.CityListResponseLocalized;
import com.opitral.ads.market.api.model.response.CityResponseLocalized;
import com.opitral.ads.market.api.services.city.CityService;
import com.opitral.ads.market.api.settings.SmartLocaleResolver;

@Slf4j
@RestController
@RequestMapping("/open-api/city")
@RequiredArgsConstructor
public class CityOpenController {

    private final CityService cityService;
    private final SmartLocaleResolver localeResolver;
    private final HttpServletRequest httpServletRequest;

    @GetMapping("/{id}")
    public ResponseEntity<Response<CityResponseLocalized>> getCityByIdOpen(@PathVariable Integer id) {
        String locale = localeResolver.resolveLocaleLanguage(httpServletRequest);
        return ResponseEntity.ok(Response.of(cityService.getCityByIdLocalized(id, locale)));
    }

    @GetMapping
    public ResponseEntity<Response<CityListResponseLocalized>> getListOfCitiesOpen(@Valid @RequestParam(required = false) String restrict) {
        String locale = localeResolver.resolveLocaleLanguage(httpServletRequest);
        return ResponseEntity.ok(Response.of(cityService.getAllCitiesLocalized(restrict, locale)));
    }

    @GetMapping("/count")
    public ResponseEntity<CommonResponse.LongResponse> countCitiesOpen(@Valid @RequestParam(required = false) String restrict) {
        return ResponseEntity.ok(CommonResponse.LongResponse.of(cityService.count(restrict)));
    }

}