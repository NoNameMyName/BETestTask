package com.winwintravel.authapi.service;

import com.winwintravel.authapi.dto.JwtAuthenticationDto;
import com.winwintravel.authapi.dto.RefreshTokenDto;
import com.winwintravel.authapi.dto.UserCredentialsDto;
import com.winwintravel.authapi.dto.UserDTO;
import com.winwintravel.authapi.entity.User;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    JwtAuthenticationDto singIn(UserCredentialsDto userCredentialsDto) throws AuthenticationException;
    JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception;
    UserDTO getUserByEmail(String email);
    void saveUser(User user);
    UUID findUuidByEmail(String email);
}
