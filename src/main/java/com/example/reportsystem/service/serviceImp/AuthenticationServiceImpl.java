package com.example.reportsystem.service.serviceImp;

import com.example.reportsystem.enums.Role;
import com.example.reportsystem.exception.GlobalException;
import com.example.reportsystem.model.User;
import com.example.reportsystem.model.request.SignUpRequest;
import com.example.reportsystem.model.request.SigninRequest;
import com.example.reportsystem.model.responses.ApiResponse;
import com.example.reportsystem.model.responses.JwtAuthenticationResponse;
import com.example.reportsystem.repository.UserRepository;
import com.example.reportsystem.service.AuthenticationService;
import com.example.reportsystem.service.JwtService;
import com.example.reportsystem.utilities.response.EmptyObject;
import com.example.reportsystem.utilities.response.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final
    EmptyObject emptyObject = new EmptyObject();
    Message message = new Message();
    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .token(jwt).build();
    }
    @Override
    public  ResponseEntity<?> signin(SigninRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

            var jwt = jwtService.generateToken(user);


            return ResponseEntity.ok(JwtAuthenticationResponse.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .role(user.getRole().name())
                    .token(jwt).build());
        } catch (AuthenticationException e) {
            // Handle authentication failure
            emptyObject.setStatus(false);
            emptyObject.setMessage(message.loginFail());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(emptyObject);
        }
    }
}
