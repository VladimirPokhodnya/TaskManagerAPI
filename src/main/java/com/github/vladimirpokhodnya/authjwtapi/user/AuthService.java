package com.github.vladimirpokhodnya.authjwtapi.user;

import com.github.vladimirpokhodnya.authjwtapi.config.JwtUtil;
import com.github.vladimirpokhodnya.authjwtapi.exception.InvalidCredentialsException;
import com.github.vladimirpokhodnya.authjwtapi.exception.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

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
}
