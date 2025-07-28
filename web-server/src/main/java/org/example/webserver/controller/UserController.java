package org.example.webserver.controller;

import lombok.RequiredArgsConstructor;
import org.example.logging.log_target.LogExecutionTime;
import org.example.security.model.AuthenticationRequestDTO;
import org.example.security.model.AuthenticationResponseDTO;
import org.example.security.roleaccess.AnyRoleAccess;
import org.example.security.service.AuthenticationService;
import org.example.security.model.RegisterRequestDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final AuthenticationService service;

    @AnyRoleAccess
    @LogExecutionTime
    @PostMapping("/register")
    public AuthenticationResponseDTO register(@RequestBody RegisterRequestDTO requestDTO) {
        return service.register(requestDTO);
    }

    @AnyRoleAccess
    @LogExecutionTime
    @PostMapping("/login")
    public AuthenticationResponseDTO login(@RequestBody AuthenticationRequestDTO requestDTO) {
        return service.authenticate(requestDTO);
    }



}
