package com.deedee.profile.dto;

import com.deedee.profile.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileChangeLogDto {

    private LocalDateTime updateAt;

    private Profile profile;

    private String avatarUrl;

    private String fullName;

    private long birthday;

    private String phoneNumber;

    private String email;

    private String bio;

}
