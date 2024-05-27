package com.example.reportsystem.service.report;

import com.example.reportsystem.model.request.ReportRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;


public interface ReportService {
    ResponseEntity<?> findAllReport(Integer pageNumber, Integer pageSize, String sortBy, boolean ascending);
    ResponseEntity<?> findReportByTimeBetween(Integer pageNumber, Integer pageSize, String sortBy, boolean ascending, String startDate, String endDate);
    ResponseEntity<?> findByUserId(Integer id);
    ResponseEntity<?> addNew(ReportRequest request);
    ResponseEntity<?> findReportCurrentUser(Integer pageNumber, Integer pageSize, String sortBy, boolean ascending);
    ResponseEntity<?> UpdateById(ReportRequest request,Integer id);
    ResponseEntity<?> deleteById(Integer id);
    ResponseEntity<?> getReportById(Integer id);
}
