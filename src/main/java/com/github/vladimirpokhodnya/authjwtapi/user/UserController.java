package com.github.vladimirpokhodnya.authjwtapi.user;

import com.github.vladimirpokhodnya.authjwtapi.exception.InvalidCredentialsException;
import com.github.vladimirpokhodnya.authjwtapi.exception.UserNotFoundException;
import com.github.vladimirpokhodnya.authjwtapi.user.dto.AuthResponse;
import com.github.vladimirpokhodnya.authjwtapi.user.dto.ErrorResponse;
import com.github.vladimirpokhodnya.authjwtapi.user.dto.UserLoginDTO;
import com.github.vladimirpokhodnya.authjwtapi.user.dto.UserRegisterDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody UserRegisterDTO user) {
        AuthResponse authResponse = userService.registerUser(user);
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO loginDTO) {
        try {
            String token = authService.authenticate(loginDTO.name(), loginDTO.password());
            AuthResponse authResponse = new AuthResponse(token);
            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + token)
                    .body(authResponse);
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(ex.getMessage()));
        } catch (InvalidCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(ex.getMessage()));
        }
    }



    @PostMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

}

