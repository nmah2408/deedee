package com.deedee.profile.exception;

public class ProfileNotFoundException extends RuntimeException {
    public ProfileNotFoundException(String message) {
        super(message);
    }
}
