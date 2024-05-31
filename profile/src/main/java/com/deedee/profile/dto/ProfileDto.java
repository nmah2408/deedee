package com.deedee.profile.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileDto {

    private String id;

    private String avatarUrl;

    private String fullName;

    private long birthday;

    private String phoneNumber;

    private String email;

    private String bio;

    private LocalDateTime createAt;
}
