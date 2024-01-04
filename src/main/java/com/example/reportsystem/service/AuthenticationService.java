package com.example.reportsystem.service;

import com.example.reportsystem.model.request.SignUpRequest;
import com.example.reportsystem.model.request.SigninRequest;
import com.example.reportsystem.model.responses.JwtAuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);

}
