package com.github.vladimirpokhodnya.authjwtapi.token.controller;

import com.github.vladimirpokhodnya.authjwtapi.token.exception.InvalidCredentialsException;
import com.github.vladimirpokhodnya.authjwtapi.token.exception.UserNotFoundException;
import com.github.vladimirpokhodnya.authjwtapi.token.AuthService;
import com.github.vladimirpokhodnya.authjwtapi.user.dto.UserLoginDTO;
import com.github.vladimirpokhodnya.authjwtapi.user.dto.UserRegisterDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody UserRegisterDTO registerDTO) {
        AuthResponse authResponse = authService.registerUser(registerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO loginDTO) {
        try {
            String token = authService.authenticate(loginDTO.name(), loginDTO.password());
            AuthResponse authResponse = new AuthResponse(token);
            return ResponseEntity.ok()
//                    .header("Authorization", "Bearer " + token)
                    .body(authResponse);
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(ex.getMessage()));
        } catch (InvalidCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(ex.getMessage()));
        }
    }

}


