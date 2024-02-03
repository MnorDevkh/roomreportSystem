package com.example.reportsystem.service;

import com.example.reportsystem.model.request.TeacherRequest;
import org.springframework.http.ResponseEntity;

public interface LecturerService {
    ResponseEntity<?> findAllTeacher();
//    ResponseEntity<?>
    ResponseEntity<?> save(TeacherRequest teacherRequest);
}
