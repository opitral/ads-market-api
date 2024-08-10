package com.opitral.ads.market.api.controllers.rest.subject;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.opitral.ads.market.api.common.response.CommonResponse;
import com.opitral.ads.market.api.model.response.SubjectListResponse;
import com.opitral.ads.market.api.model.response.SubjectResponse;
import com.opitral.ads.market.api.model.view.SubjectView;
import com.opitral.ads.market.api.services.subject.SubjectService;
import com.opitral.ads.market.api.common.response.Response;

@Slf4j
@RestController
@RequestMapping("/api/subject")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<CommonResponse.IntegerResponse> createSubject(@Valid @RequestBody SubjectView body) {
//        log.info("User {} wants to create subject {}", SecurityContextAccessor.getTelegramID(), body);
        return ResponseEntity.ok(CommonResponse.IntegerResponse.of(subjectService.create(body)));
    }

    @PutMapping
    public ResponseEntity<CommonResponse.BooleanResponse> updateSubject(@Valid @RequestBody SubjectView body) {
//        log.info("User {} wants to update subject {}", SecurityContextAccessor.getTelegramID(), body);
        return ResponseEntity.ok(CommonResponse.BooleanResponse.of(subjectService.update(body)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse.BooleanResponse> deleteSubjectById(@PathVariable Integer id) {
//        log.info("User {} wants to delete subject {}", SecurityContextAccessor.getTelegramID(), id);
        return ResponseEntity.ok(CommonResponse.BooleanResponse.of(subjectService.delete(id)));
    }

    @GetMapping
    public ResponseEntity<Response<SubjectListResponse>> getListOfSubjects(@Valid @RequestParam(required = false) String restrict) {
        return ResponseEntity.ok(Response.of(subjectService.getAllSubjects(restrict)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<SubjectResponse>> getSubjectById(@PathVariable Integer id) {
        return ResponseEntity.ok(Response.of(subjectService.getSubjectById(id)));
    }

    @GetMapping("/count")
    public ResponseEntity<CommonResponse.LongResponse> countSubjects(@Valid @RequestParam(required = false) String restrict) {
        return ResponseEntity.ok(CommonResponse.LongResponse.of(subjectService.count(restrict)));
    }
}
