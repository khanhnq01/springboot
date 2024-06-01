package com.khanhnq.identity.controller;

import com.khanhnq.identity.dto.request.ApiResponse;
import com.khanhnq.identity.dto.request.AuthenticationRequest;
import com.khanhnq.identity.dto.request.IntrospectRequest;
import com.khanhnq.identity.dto.response.AuthenticationResponse;
import com.khanhnq.identity.dto.response.IntrospectResponse;
import com.khanhnq.identity.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService authenticationService;
    final ThreadLocal<String> SIGNER_KEY = new ThreadLocal<String>();

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/authenticate")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);
        boolean authenticated = authenticationResponse.isAuthenticated();
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationResponse)
                .build();
    }

    @PostMapping("/introspect")
    public IntrospectResponse introspect(IntrospectRequest request) {
        try {
            var token = request.getToken();

            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.get().getBytes());

            SignedJWT signedJWT = SignedJWT.parse(token);

            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

            var verified = signedJWT.verify(verifier);

            return IntrospectResponse.builder()
                    .valid(verified && expiryTime.after(new Date()))
                    .build();
        } catch (ParseException e) {
            log.error("Failed to parse token", e);
            return IntrospectResponse.builder()
                    .valid(false)
                    .build();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

}
