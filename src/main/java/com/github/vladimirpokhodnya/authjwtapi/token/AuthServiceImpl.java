package com.github.vladimirpokhodnya.authjwtapi.token;

import com.github.vladimirpokhodnya.authjwtapi.token.controller.AuthResponse;
import com.github.vladimirpokhodnya.authjwtapi.token.exception.InvalidCredentialsException;
import com.github.vladimirpokhodnya.authjwtapi.token.exception.UserNotFoundException;
import com.github.vladimirpokhodnya.authjwtapi.user.UserEntity;
import com.github.vladimirpokhodnya.authjwtapi.user.UserService;
import com.github.vladimirpokhodnya.authjwtapi.user.dto.UserRegisterDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String authenticate(String username, String password) throws UserNotFoundException, InvalidCredentialsException {
        Optional<UserEntity> user = userService.getUserByName(username);
        if (user.isEmpty()) {
            throw new UserNotFoundException("Пользователь не найден");
        }

        if (passwordEncoder.matches(password, user.get().getPassword())) {
            return jwtUtil.generateToken(user.get().getName(), user.get().getEmail());
        } else {
            throw new InvalidCredentialsException("Неверный пароль");
        }
    }

    public AuthResponse registerUser(UserRegisterDTO registerDTO) {
        return userService.registerUser(registerDTO);
    }
}

