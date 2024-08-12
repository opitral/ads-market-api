package com.opitral.ads.market.api.controllers.rest.subject;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.opitral.ads.market.api.common.response.CommonResponse;
import com.opitral.ads.market.api.common.response.Response;
import com.opitral.ads.market.api.model.response.SubjectListResponseLocalized;
import com.opitral.ads.market.api.model.response.SubjectResponseLocalized;
import com.opitral.ads.market.api.services.subject.SubjectService;
import com.opitral.ads.market.api.settings.SmartLocaleResolver;

@Slf4j
@RestController
@RequestMapping("/open-api/subject")
@RequiredArgsConstructor
public class SubjectOpenController {

    private final SubjectService subjectService;
    private final SmartLocaleResolver localeResolver;
    private final HttpServletRequest httpServletRequest;

    @GetMapping("/{id}")
    public ResponseEntity<Response<SubjectResponseLocalized>> getSubjectByIdOpen(@PathVariable Integer id) {
        String locale = localeResolver.resolveLocaleLanguage(httpServletRequest);
        return ResponseEntity.ok(Response.of(subjectService.getSubjectByIdLocalized(id, locale)));
    }

    @GetMapping
    public ResponseEntity<Response<SubjectListResponseLocalized>> getListOfSubjectsOpen(@Valid @RequestParam(required = false) String restrict) {
        String locale = localeResolver.resolveLocaleLanguage(httpServletRequest);
        return ResponseEntity.ok(Response.of(subjectService.getAllSubjectsLocalized(restrict, locale)));
    }

    @GetMapping("/count")
    public ResponseEntity<CommonResponse.LongResponse> countSubjectsOpen(@Valid @RequestParam(required = false) String restrict) {
        return ResponseEntity.ok(CommonResponse.LongResponse.of(subjectService.count(restrict)));
    }

}
