package com.deedee.profile.service;

import com.deedee.profile.dto.ProfileDto;

import java.util.List;

public interface ProfileService {

    ProfileDto createProfile (String email);

    ProfileDto currentProfile ();

    ProfileDto getProfile (String email);

    List<ProfileDto> getAllProfile ();

    ProfileDto updateProfile (String id, ProfileDto profileDto);

    void createProfileChangeLog (ProfileDto profileDto);
}
