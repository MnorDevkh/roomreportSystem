package com.example.reportsystem.service;

import com.example.reportsystem.model.request.UserShiftRequest;
import com.example.reportsystem.model.request.UserSubjectRequest;
import org.springframework.http.ResponseEntity;

public interface UserShiftService {
    ResponseEntity<?> save(UserShiftRequest userShiftRequest);
    ResponseEntity<?> deleteById(long id);
}
