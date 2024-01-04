package com.example.reportsystem.service;

import com.example.reportsystem.model.request.TeacherRequest;
import org.springframework.http.ResponseEntity;

public interface TeacherService {
    ResponseEntity<?> findAllTeacher();
    ResponseEntity<?> save(TeacherRequest teacherRequest);
}
