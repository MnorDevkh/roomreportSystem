package com.example.reportsystem.service;

import com.example.reportsystem.model.request.UserRequest;
import org.springframework.http.ResponseEntity;

public interface LecturerService {
    ResponseEntity<?> findAllTeacher();
//    ResponseEntity<?>
    ResponseEntity<?> save(UserRequest userRequest);
}
