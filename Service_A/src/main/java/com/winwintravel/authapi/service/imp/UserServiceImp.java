package com.winwintravel.authapi.service.imp;

import com.winwintravel.authapi.dto.JwtAuthenticationDto;
import com.winwintravel.authapi.dto.RefreshTokenDto;
import com.winwintravel.authapi.dto.UserCredentialsDto;
import com.winwintravel.authapi.dto.UserDTO;
import com.winwintravel.authapi.dto.mapping.UserMapping;
import com.winwintravel.authapi.entity.User;
import com.winwintravel.authapi.repository.UserRepository;
import com.winwintravel.authapi.security.jwt.JwtService;
import com.winwintravel.authapi.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final UserMapping userMapping;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JwtAuthenticationDto singIn(UserCredentialsDto userCredentialsDto) throws AuthenticationException {
        User user = findByCredentials(userCredentialsDto);
        return jwtService.generateAuthToken(user.getEmail());
    }

    @Override
    public JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception {
        String refreshToken = refreshTokenDto.getRefreshToken();
        if (refreshToken != null && jwtService.validateToken(refreshToken)) {
            User user = findByEmail(jwtService.getEmailFromToken(refreshToken));
            return jwtService.refreshBaseToken(user.getEmail(), refreshToken);
        }
        throw new AuthenticationException("Invalid refresh token");
    }

    @Override
    @Transactional
    public UserDTO getUserByEmail(String email){
        return userRepository.findByEmail(email).map(userMapping::toDto).orElse(null);
    }

    @Override
    @Transactional
    public void saveUser(User user) {
//        System.out.println(">>> DTO password: " + userDTO.getPasswordHash());
//        User user = userMapping.toEntity(userDTO);
//        System.out.println(">>> User password: " + user.getPasswordHash());
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UUID findUuidByEmail(String email) {
        return userRepository.findUuidByEmail(email);
    }

    private User findByCredentials(UserCredentialsDto userCredentialsDto) throws AuthenticationException {
        Optional<User> optionalUser = userRepository.findByEmail(userCredentialsDto.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(userCredentialsDto.getPassword(), user.getPasswordHash())) {
                return user;
            }
        }
        throw new AuthenticationException("Email or password is not correct");
    }

    private User findByEmail(String email) throws Exception {
        return userRepository.findByEmail(email).orElseThrow(() -> new Exception(String.format("User with email %s not found", email)));
    }

}
