package com.winwintravel.authapi.controller;

import com.winwintravel.authapi.dto.JwtAuthenticationDto;
import com.winwintravel.authapi.dto.RefreshTokenDto;
import com.winwintravel.authapi.dto.UserCredentialsDto;
import com.winwintravel.authapi.dto.UserDTO;
import com.winwintravel.authapi.entity.User;
import com.winwintravel.authapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationDto> signIn(@RequestBody UserCredentialsDto userCredentialsDto) {
        try {
            JwtAuthenticationDto jwtAuthenticationDto = userService.singIn(userCredentialsDto);
            return ResponseEntity.ok(jwtAuthenticationDto);
        }
        catch (AuthenticationException e) {
            throw new RuntimeException("Authentication Failed" + e.getMessage());
        }
    }

    @PostMapping("/refresh")
    public JwtAuthenticationDto refresh(@RequestBody RefreshTokenDto refreshTokenDto) throws Exception {
        return userService.refreshToken(refreshTokenDto);
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        userService.saveUser(user);
        return "User registered successfully";
    }
}
