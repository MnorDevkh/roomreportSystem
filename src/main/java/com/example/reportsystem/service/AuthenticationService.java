package com.example.reportsystem.service;

import com.example.reportsystem.model.request.SignUpRequest;
import com.example.reportsystem.model.request.SigninRequest;
import com.example.reportsystem.model.responses.JwtAuthenticationResponse;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    ResponseEntity<?> signin(SigninRequest request);

}
