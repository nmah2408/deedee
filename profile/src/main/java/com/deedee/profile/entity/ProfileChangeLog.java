package com.deedee.profile.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "profile_change_log")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProfileChangeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime updateAt;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    private String avatarUrl;

    private String fullName;

    private long birthday;

    private String phoneNumber;

    private String email;

    private String bio;
}
