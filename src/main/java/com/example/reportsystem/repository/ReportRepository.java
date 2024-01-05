package com.example.reportsystem.repository;

import com.example.reportsystem.model.Report;
import com.example.reportsystem.model.Teacher;
import com.example.reportsystem.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
    Optional<Report> findByTeacherId(Integer teacherId);
    // Custom query to find reports by teacher
    List<Report> findByTeacher(Teacher teacher);
    Page<Report> findAll(Pageable pageable);
}
