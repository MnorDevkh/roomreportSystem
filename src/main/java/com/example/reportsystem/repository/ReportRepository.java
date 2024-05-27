package com.example.reportsystem.repository;

import com.example.reportsystem.model.report.Report;
import com.example.reportsystem.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

    Page<Report> findAll(Pageable pageable);

    Page<Report> findByDeletedFalse(PageRequest pageable);

    Page<Report> findByDeletedFalseAndUser(PageRequest pageable,User user);
    Page<Report> findByDeletedFalseAndCreateDateBetween(LocalDate startCreateDate, LocalDate endCreateDate, Pageable pageable);


}
