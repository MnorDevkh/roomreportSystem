package com.example.reportsystem.service;

import com.example.reportsystem.model.request.ShiftRequest;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public interface ShiftService {
    ResponseEntity<?> findAll();
    ResponseEntity<?> findShiftCurrenUser(Integer pageNumber, Integer pageSize, String sortBy, boolean ascending);

    ResponseEntity<?> findById(Integer id);

    ResponseEntity<?> save(ShiftRequest shiftRequest);

    ResponseEntity<?> deleteById(Integer id);

    ResponseEntity<?> updateById(Integer id ,ShiftRequest shiftRequest);

    ResponseEntity<?> findShiftByUserId(long userId);
}
