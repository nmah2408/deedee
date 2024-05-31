package com.deedee.identity.repository;

import com.deedee.identity.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findByRefreshToken (String refreshToken);
}
