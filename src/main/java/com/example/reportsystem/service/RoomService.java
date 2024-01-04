package com.example.reportsystem.service;

import com.example.reportsystem.model.request.RoomRequest;
import org.springframework.http.ResponseEntity;

public interface RoomService {
    ResponseEntity<?> findAll();
    ResponseEntity<?> save(RoomRequest request);
}
