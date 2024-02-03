package com.example.reportsystem.service;

import com.example.reportsystem.model.request.UserSubjectRequest;
import org.springframework.http.ResponseEntity;

public interface UserSubjectService {
    ResponseEntity<?> save(UserSubjectRequest userSubjectRequest);

    ResponseEntity<?> deleteById(long id);
}
