package com.deedee.identity.service;

import com.deedee.identity.entity.Account;
import com.deedee.identity.entity.RefreshToken;
import com.deedee.identity.exception.TokenExpiredException;
import com.deedee.identity.exception.TokenNotFoundException;
import com.deedee.identity.repository.AccountRepository;
import com.deedee.identity.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final AccountRepository accountRepository;

    @NonFinal
    @Value("${jwt.refreshTokenTime}")
    protected long refreshTokenTime;

    public RefreshToken createRefreshToken(String username) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
        RefreshToken refreshToken = account.getRefreshToken();

        if (refreshToken == null) {
            refreshToken = RefreshToken.builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .expirationTime(Instant.now().plusMillis(refreshTokenTime))
                    .account(accountRepository.findByUsername(username)
                            .orElseThrow(() -> new UsernameNotFoundException(username + " not found")))
                    .build();

            refreshTokenRepository.save(refreshToken);
        }

        return refreshToken;
    }

    public RefreshToken verifyRefreshToken(String refreshToken) {
        RefreshToken refreshTokenOb = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new TokenNotFoundException("Refresh Token not exist"));

        if (refreshTokenOb.getExpirationTime().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshTokenOb);
            throw new TokenExpiredException("Refresh Token expired");
        }

        return refreshTokenOb;
    }
}
