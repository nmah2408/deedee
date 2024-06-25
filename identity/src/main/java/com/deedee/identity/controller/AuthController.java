package com.deedee.identity.controller;

import com.deedee.identity.entity.Account;
import com.deedee.identity.entity.RefreshToken;
import com.deedee.identity.repository.feignClient.ProfileFeignClient;
import com.deedee.identity.request.LoginRequest;
import com.deedee.identity.request.RefreshTokenRequest;
import com.deedee.identity.request.RegisterRequest;
import com.deedee.identity.response.AuthResponse;
import com.deedee.identity.response.SuccessApiResponse;
import com.deedee.identity.service.AuthService;
import com.deedee.identity.service.JwtService;
import com.deedee.identity.service.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final ProfileFeignClient profileFeignClient;

    // endpoint to register new user
    @PostMapping("/register")
    public ResponseEntity<SuccessApiResponse<AuthResponse>> register (@Valid @RequestBody RegisterRequest request) {
        profileFeignClient.createProfile(request.getUsername());
        AuthResponse register = authService.register(request);
        return ResponseEntity.ok(SuccessApiResponse.buildResponse(register));
    }

    // endpoint to authenticate user for login
    @PostMapping("/login")
    public ResponseEntity<SuccessApiResponse<AuthResponse>> login (@Valid @RequestBody LoginRequest request) {
        AuthResponse login = authService.login(request);
        return ResponseEntity.ok(SuccessApiResponse.buildResponse(login));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<SuccessApiResponse<AuthResponse>> refreshToken (@RequestBody RefreshTokenRequest request) {

        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(request.getRefreshToken());
        Account account = refreshToken.getAccount();
        String token = this.jwtService.generateToken(account);
        AuthResponse build = AuthResponse.builder()
                .refreshToken(refreshToken.getRefreshToken())
                .accessToken(token)
                .build();

        return ResponseEntity.ok(SuccessApiResponse.buildResponse(build));
    }
}

