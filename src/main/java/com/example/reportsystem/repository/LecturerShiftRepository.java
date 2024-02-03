package com.example.reportsystem.repository;

import com.example.reportsystem.model.Lecturer;
import com.example.reportsystem.model.LecturerShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LecturerShiftRepository extends JpaRepository<LecturerShift,Long> {
    List<LecturerShift> findAllByLecturer(Lecturer lecturer);
}
