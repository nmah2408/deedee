package com.deedee.profile.service.impl;

import com.deedee.profile.dto.ProfileDto;
import com.deedee.profile.entity.Profile;
import com.deedee.profile.entity.ProfileChangeLog;
import com.deedee.profile.exception.ProfileNotFoundException;
import com.deedee.profile.mapper.ProfileChangeLogMapper;
import com.deedee.profile.mapper.ProfileMapper;
import com.deedee.profile.repository.ProfileChangeLogRepository;
import com.deedee.profile.repository.ProfileRepository;
import com.deedee.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    private final ProfileChangeLogRepository profileChangeLogRepository;

    @Override
    public ProfileDto createProfile(String email) {

        Profile profile = Profile.builder()
                .email(email)
                .avatarUrl("")
                .fullName("")
                .birthday(Instant.now().toEpochMilli())
                .phoneNumber("")
                .bio("")
                .createAt(LocalDateTime.now())
                .build();

        profileRepository.save(profile);

        return ProfileMapper.INSTANCE.toProfileDto(profile);
    }

    @Override
    public ProfileDto currentProfile() {
        return null;
    }

    @Override
    public ProfileDto getProfile(String email) {

        Profile profile = profileRepository.findByEmail(email)
                .orElseThrow(() -> new ProfileNotFoundException("Profile email [" + email + "] not found"));

        return ProfileMapper.INSTANCE.toProfileDto(profile);
    }

    @Override
    public List<ProfileDto> getAllProfile() {

        List<Profile> profiles = profileRepository.findAll();
        List<ProfileDto> profileDtoList = new ArrayList<>();
        for (Profile profile : profiles) {
            profileDtoList.add(ProfileMapper.INSTANCE.toProfileDto(profile));
        }

        return profileDtoList;
    }

    @Override
    public ProfileDto updateProfile(String id, ProfileDto profileDto) {

        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException("Profile id [" + id + "] not found"));

        ProfileMapper.INSTANCE.updateProfile(profile, profileDto);
        ProfileDto savedDto = ProfileMapper.INSTANCE.toProfileDto(profile);
        createProfileChangeLog(savedDto);

        return savedDto;
    }

    @Override
    public void createProfileChangeLog(ProfileDto profileDto) {

        ProfileChangeLog profileChangeLog = ProfileChangeLogMapper.INSTANCE.profileToProfileChangLog(profileDto);
        profileChangeLogRepository.save(profileChangeLog);
    }
}
