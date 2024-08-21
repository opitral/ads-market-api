package com.opitral.ads.market.api.controllers.rest;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.opitral.ads.market.api.common.response.CommonResponse;
import com.opitral.ads.market.api.common.response.Response;
import com.opitral.ads.market.api.model.response.UserListResponse;
import com.opitral.ads.market.api.model.response.UserResponse;
import com.opitral.ads.market.api.model.view.UserView;
import com.opitral.ads.market.api.services.user.UserService;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<CommonResponse.IntegerResponse> createUser(@Valid @RequestBody UserView body) {
        return ResponseEntity.ok(CommonResponse.IntegerResponse.of(userService.create(body)));
    }

    @PutMapping
    public ResponseEntity<CommonResponse.BooleanResponse> updateUser(@Valid @RequestBody UserView body) {
        return ResponseEntity.ok(CommonResponse.BooleanResponse.of(userService.update(body)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse.BooleanResponse> deleteUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(CommonResponse.BooleanResponse.of(userService.delete(id)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<UserResponse>> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(Response.of(userService.getUserById(id)));
    }

    @GetMapping("/telegram/{id}")
    public ResponseEntity<Response<UserResponse>> getUserByTelegramId(@PathVariable String id) {
        return ResponseEntity.ok(Response.of(userService.getUserByTelegramId(id)));
    }

    @GetMapping
    public ResponseEntity<Response<UserListResponse>> getListOfUsers(@Valid @RequestParam(required = false) String restrict) {
        return ResponseEntity.ok(Response.of(userService.getAllUsers(restrict)));
    }

}
