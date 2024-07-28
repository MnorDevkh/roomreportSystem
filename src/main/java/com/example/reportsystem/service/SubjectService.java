package com.example.reportsystem.service;

import com.example.reportsystem.model.Subject;
import com.example.reportsystem.model.User;
import com.example.reportsystem.model.request.ShiftRequest;
import com.example.reportsystem.model.request.SubjectRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
public interface SubjectService {
    ResponseEntity<?> findAllSubject();
    ResponseEntity<?> findSubject();
    ResponseEntity<?> addSubject(SubjectRequest subjectRequest);
    ResponseEntity<?> findSubjectByUser(long id);
    ResponseEntity<?> deleteById(Integer id);
    ResponseEntity<?> updateById(Integer id, SubjectRequest subjectRequest);
    ResponseEntity<?> findById(Integer id);
}
