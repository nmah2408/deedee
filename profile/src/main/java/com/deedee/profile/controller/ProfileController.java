package com.deedee.profile.controller;

import com.deedee.profile.dto.ProfileDto;
import com.deedee.profile.entity.Profile;
import com.deedee.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/create")
    public ResponseEntity<ProfileDto> createProfile (@RequestBody String email) {
        return ResponseEntity.ok(profileService.createProfile(email));
    }

    @GetMapping("/get/{email}")
    public ResponseEntity<ProfileDto> getProfile (@PathVariable String email) {
        return ResponseEntity.ok(profileService.getProfile(email));
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<ProfileDto>> getAllProfiles () {
        return ResponseEntity.ok(profileService.getAllProfile());
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<ProfileDto> updateProfile (@PathVariable String id, @RequestBody ProfileDto profileDto) {
        return ResponseEntity.ok(profileService.updateProfile(id, profileDto));
    }
}
