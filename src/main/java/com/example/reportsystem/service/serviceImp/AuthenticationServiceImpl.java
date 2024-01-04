package com.example.reportsystem.service.serviceImp;

import com.example.reportsystem.enums.Role;
import com.example.reportsystem.model.User;
import com.example.reportsystem.model.request.SignUpRequest;
import com.example.reportsystem.model.request.SigninRequest;
import com.example.reportsystem.model.responses.JwtAuthenticationResponse;
import com.example.reportsystem.repository.UserRepository;
import com.example.reportsystem.service.AuthenticationService;
import com.example.reportsystem.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder()
                .id(user.getId().toString())
                .email(user.getEmail())
                .fistName(user.getFirstName())
                .lastName(user.getLastName())
                .token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder()
                .id(user.getId().toString())
                .email(user.getEmail())
                .fistName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().name())
                .token(jwt).build();
    }
}
