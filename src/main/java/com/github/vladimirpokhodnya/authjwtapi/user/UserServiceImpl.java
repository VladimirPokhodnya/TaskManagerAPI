package com.github.vladimirpokhodnya.authjwtapi.user;

import com.github.vladimirpokhodnya.authjwtapi.token.JwtUtil;
import com.github.vladimirpokhodnya.authjwtapi.token.controller.AuthResponse;
import com.github.vladimirpokhodnya.authjwtapi.token.exception.UserAlreadyExistsException;
import com.github.vladimirpokhodnya.authjwtapi.user.dto.UserMapper;
import com.github.vladimirpokhodnya.authjwtapi.user.dto.UserRegisterDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;

    @Transactional
    @Override
    public AuthResponse registerUser(UserRegisterDTO registerDTO) {

        List<String> errors = new ArrayList<>();

        if (userRepository.findByName(registerDTO.name()).isPresent()) {
            errors.add("Пользователь с таким именем уже существует.");
        }

        if (userRepository.findByEmail(registerDTO.email()).isPresent()) {
            errors.add("Пользователь с таким email уже существует.");
        }

        if (!errors.isEmpty()) {
            throw new UserAlreadyExistsException(String.join(" ", errors));
        }

        UserEntity user = userMapper.toEntity(registerDTO);
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        UserEntity savedUser = userRepository.save(user);

        String token = jwtUtil.generateToken(savedUser.getName(), savedUser.getEmail());

        return new AuthResponse(token);
    }

    public Optional<UserEntity> getUserByName(String username) {
        return userRepository.findByName(username);
    }
}



