package com.opitral.ads.market.api.controllers.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.opitral.ads.market.api.common.response.CommonResponse;
import com.opitral.ads.market.api.common.response.Response;
import com.opitral.ads.market.api.model.response.PostListResponse;
import com.opitral.ads.market.api.model.response.PostResponse;
import com.opitral.ads.market.api.model.view.PostView;
import com.opitral.ads.market.api.services.post.PostService;

@Slf4j
@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<CommonResponse.IntegerResponse> createPost(@Valid @RequestBody PostView body) {
        return ResponseEntity.ok(CommonResponse.IntegerResponse.of(postService.create(body)));
    }

    @PutMapping
    public ResponseEntity<CommonResponse.BooleanResponse> updatePost(@Valid @RequestBody PostView body) {
        return ResponseEntity.ok(CommonResponse.BooleanResponse.of(postService.update(body)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse.BooleanResponse> deletePostById(@PathVariable Integer id) {
        return ResponseEntity.ok(CommonResponse.BooleanResponse.of(postService.delete(id)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<PostResponse>> getPostById(@PathVariable Integer id) {
        return ResponseEntity.ok(Response.of(postService.getPostById(id)));
    }

    @GetMapping
    public ResponseEntity<Response<PostListResponse>> getListOfPosts(@Valid @RequestParam(required = false) String restrict) {
        return ResponseEntity.ok(Response.of(postService.getAllPosts(restrict)));
    }

}