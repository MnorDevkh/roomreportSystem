package com.example.reportsystem.repository;

import com.example.reportsystem.model.Report;
import com.example.reportsystem.model.Lecturer;
import com.example.reportsystem.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
//    Optional<Report> findBylecturerId(Long lecturer);
    // Custom query to find reports by lecturer
    List<Report> findByUser(User usere);
    Page<Report> findAll(Pageable pageable);
    List<Report> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
