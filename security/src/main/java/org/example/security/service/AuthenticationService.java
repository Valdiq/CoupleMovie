package org.example.security.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.security.model.AuthenticationRequestDTO;
import org.example.security.model.AuthenticationResponseDTO;
import org.example.security.model.RegisterRequestDTO;
import org.example.security.exception.UserNotFoundException;
import org.example.security.entity.UserEntity;
import org.example.security.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Validated
public class AuthenticationService {

    private final UserRepository repository;

    private final PasswordEncoder encoder;

    private final JWTService jwtService;

    private final AuthenticationManager manager;

    public AuthenticationResponseDTO register(@Valid RegisterRequestDTO requestDTO) {
        var user = new UserEntity()
                .setEmail(requestDTO.email())
                .setUsername(requestDTO.username())
                .setPassword(encoder.encode(requestDTO.password()));

        var jwtToken = jwtService.generateToken(user);
        repository.save(user);

        return new AuthenticationResponseDTO(jwtToken);
    }

    public AuthenticationResponseDTO authenticate(@Valid AuthenticationRequestDTO requestDTO) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(requestDTO.username(), requestDTO.password()));

        var user = getUserByUsername(requestDTO.username());

        var jwtToken = jwtService.generateToken(user);

        return new AuthenticationResponseDTO(jwtToken);
    }

    @Transactional(readOnly = true)
    public UserEntity getUserByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username " + username + " not found"));
    }
}
