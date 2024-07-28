package com.example.reportsystem.service;

import com.example.reportsystem.model.request.RoomRequest;
import org.springframework.http.ResponseEntity;

public interface RoomService {
    ResponseEntity<?> findAll();
    ResponseEntity<?> findById(Integer id);
    ResponseEntity<?> save(RoomRequest request);
    ResponseEntity<?> deleteById(Integer id);
    ResponseEntity<?> updateById(RoomRequest request,Integer id);
}
