package com.deedee.identity.service;

import com.deedee.identity.entity.Account;
import com.deedee.identity.entity.Role;
import com.deedee.identity.repository.AccountRepository;
import com.deedee.identity.repository.RoleRepository;
import com.deedee.identity.request.LoginRequest;
import com.deedee.identity.request.RegisterRequest;
import com.deedee.identity.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    public AuthResponse register(RegisterRequest request) {
        // get role USER
        Optional<Role> ROLE_USER = roleRepository.findByName("User");
        Set<Role> roles = new HashSet<>();
        ROLE_USER.ifPresent(roles::add);

        var account = Account.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .build();

        Account savedAccount= accountRepository.save(account);
        var jwt = jwtService.generateToken(savedAccount);
        var refreshToken = refreshTokenService.createRefreshToken(savedAccount.getUsername());
        return AuthResponse.builder()
                .accessToken(jwt)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }

    /*
     * LOGIN USER
     * using AuthenticationManager to authenticate user
     * fetch user details
     * generate JWT/refreshToken and send response
     */
    public AuthResponse login (LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        var account = accountRepository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwt = jwtService.generateToken(account);
        var refreshToken = refreshTokenService.createRefreshToken(account.getUsername());
        return AuthResponse.builder()
                .accessToken(jwt)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }
}
