package com.khanhnq.identity.controller;

import com.khanhnq.identity.dto.request.APIResponse;
import com.khanhnq.identity.dto.request.UserCreationRequest;
import com.khanhnq.identity.dto.request.UserUpdateRequest;
import com.khanhnq.identity.entity.UserTable;
import com.khanhnq.identity.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping()
    APIResponse<UserTable> createUser(@RequestBody @Valid UserCreationRequest request) {
        APIResponse<UserTable> apiResponse = new APIResponse<>();

        apiResponse.setResult(userService.createUser(request));

        return apiResponse;
    }

    @GetMapping()
    List<UserTable> getUserList() {
        return userService.getUserList();
    }

    @GetMapping("/{userId}")
    UserTable getUserById(@PathVariable("userId") String userId) {
        return userService.getUserById(userId);
    }

    @PutMapping("/{userId}")
    UserTable updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return "Delete user success";
    }
}
