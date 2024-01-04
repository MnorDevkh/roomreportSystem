package com.example.reportsystem.service;

import com.example.reportsystem.model.request.ReportRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


public interface ReportService {
    ResponseEntity<?> findAllReport();
    ResponseEntity<?> addNew(ReportRequest request);
}
