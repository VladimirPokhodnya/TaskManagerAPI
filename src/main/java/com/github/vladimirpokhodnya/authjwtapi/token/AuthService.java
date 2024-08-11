package com.github.vladimirpokhodnya.authjwtapi.token;

import com.github.vladimirpokhodnya.authjwtapi.token.controller.AuthResponse;
import com.github.vladimirpokhodnya.authjwtapi.user.dto.UserRegisterDTO;

public interface AuthService {
    String authenticate(String username, String password);

    AuthResponse registerUser(UserRegisterDTO user);
}
