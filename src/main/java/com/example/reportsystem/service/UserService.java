package com.example.reportsystem.service;

import com.example.reportsystem.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    UserDetailsService userDetailsService();
}
