package com.deedee.identity.controller;

import com.deedee.identity.entity.Account;
import com.deedee.identity.entity.RefreshToken;
import com.deedee.identity.repository.feignClient.ProfileFeignClient;
import com.deedee.identity.request.LoginRequest;
import com.deedee.identity.request.RefreshTokenRequest;
import com.deedee.identity.request.RegisterRequest;
import com.deedee.identity.response.AuthResponse;
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

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final ProfileFeignClient profileFeignClient;

    // endpoint to register new user
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register (@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    // endpoint to authenticate user for login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login (@Valid @RequestBody LoginRequest request) {
        profileFeignClient.createProfile(request.getUsername());
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<AuthResponse> refreshToken (@RequestBody RefreshTokenRequest request) {

        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(request.getRefreshToken());
        Account account = refreshToken.getAccount();

        String token = this.jwtService.generateToken(account);

        return ResponseEntity.ok(AuthResponse.builder()
                .refreshToken(refreshToken.getRefreshToken())
                .accessToken(token)
                .build());
    }
}

