package com.opitral.ads.market.api.controllers.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.opitral.ads.market.api.common.response.CommonResponse;
import com.opitral.ads.market.api.common.response.Response;
import com.opitral.ads.market.api.model.response.GroupListResponse;
import com.opitral.ads.market.api.model.response.GroupResponse;
import com.opitral.ads.market.api.model.view.GroupView;
import com.opitral.ads.market.api.services.group.GroupService;

@Slf4j
@RestController
@RequestMapping("/api/group")
@RequiredArgsConstructor
public class GroupController {
    
    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<CommonResponse.IntegerResponse> createGroup(@Valid @RequestBody GroupView body) {
        return ResponseEntity.ok(CommonResponse.IntegerResponse.of(groupService.create(body)));
    }

    @PutMapping
    public ResponseEntity<CommonResponse.BooleanResponse> updateGroup(@Valid @RequestBody GroupView body) {
        return ResponseEntity.ok(CommonResponse.BooleanResponse.of(groupService.update(body)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse.BooleanResponse> deleteGroupById(@PathVariable Integer id) {
        return ResponseEntity.ok(CommonResponse.BooleanResponse.of(groupService.delete(id)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<GroupResponse>> getGroupById(@PathVariable Integer id) {
        return ResponseEntity.ok(Response.of(groupService.getGroupById(id)));
    }

    @GetMapping
    public ResponseEntity<Response<GroupListResponse>> getListOfGroups(@Valid @RequestParam(required = false) String restrict) {
        return ResponseEntity.ok(Response.of(groupService.getAllGroups(restrict)));
    }

    @GetMapping("/count")
    public ResponseEntity<CommonResponse.LongResponse> countGroups(@Valid @RequestParam(required = false) String restrict) {
        return ResponseEntity.ok(CommonResponse.LongResponse.of(groupService.count(restrict)));
    }
    
}
