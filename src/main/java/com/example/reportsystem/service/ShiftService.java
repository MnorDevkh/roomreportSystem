package com.example.reportsystem.service;

import com.example.reportsystem.model.request.ShiftRequest;
import org.springframework.http.ResponseEntity;

public interface ShiftService {
    ResponseEntity<?> findAll();
    ResponseEntity<?> save(ShiftRequest shiftRequest);
}
