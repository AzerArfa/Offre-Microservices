package com.auth.services.auth;

import com.auth.dto.SignupRequest;
import com.auth.dto.UserDto;

public interface AuthService {

    UserDto createUser(SignupRequest signupRequest);

    Boolean hasUserWithEmail(String email);

    UserDto getUserById(Long id);

    UserDto makeAdmin(Long id);
}
