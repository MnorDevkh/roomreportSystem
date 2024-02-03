package com.example.reportsystem.service;

import com.example.reportsystem.model.request.ShiftRequest;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public interface ShiftService {
    ResponseEntity<?> findAll();
    ResponseEntity<?> findShiftCurrenUser();

    ResponseEntity<?> save(ShiftRequest shiftRequest);
    ResponseEntity<?> deleteById(long id);
    ResponseEntity<?> updateById(long id ,ShiftRequest shiftRequest);
}
