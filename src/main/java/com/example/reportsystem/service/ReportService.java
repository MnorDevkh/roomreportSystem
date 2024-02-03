package com.example.reportsystem.service;

import com.example.reportsystem.model.request.ReportRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


public interface ReportService {
    ResponseEntity<?> findAllReport();
    ResponseEntity<?> findByUserId(Long id);
    ResponseEntity<?> addNew(ReportRequest request);
    ResponseEntity<?> getReportCurrentUser();
    ResponseEntity<?> UpdateById(ReportRequest request,Long id);
    ResponseEntity<?> deleteById(long id);
    ResponseEntity<?> getReportById(long id);
}
