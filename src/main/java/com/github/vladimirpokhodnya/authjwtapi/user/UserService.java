package com.github.vladimirpokhodnya.authjwtapi.user;

import com.github.vladimirpokhodnya.authjwtapi.user.dto.AuthResponse;
import com.github.vladimirpokhodnya.authjwtapi.user.dto.UserRegisterDTO;

import java.util.Optional;

public interface UserService {
    AuthResponse registerUser(UserRegisterDTO user);

    Optional<UserEntity> getUserByName(String username);

}
