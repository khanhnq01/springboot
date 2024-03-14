package com.khanhnq.identity.controller;

import com.khanhnq.identity.dto.request.APIResponse;
import com.khanhnq.identity.dto.request.AuthenticationRequest;
import com.khanhnq.identity.dto.response.AuthenticationResponse;
import com.khanhnq.identity.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class AuthenticationController {
    AuthenticationService authenticationService;
    @PostMapping("/login")
    APIResponse<AuthenticationResponse> authenticationResponseAPIResponse(@RequestBody AuthenticationRequest request) {
        boolean result = authenticationService.authenticate(request);

        return APIResponse.<AuthenticationResponse>builder()
                .result(AuthenticationResponse.builder()
                        .authenticated(result)
                        .build())
                .build();
    }
}
