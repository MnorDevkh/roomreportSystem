package com.example.reportsystem.repository;

import com.example.reportsystem.model.ReportRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRoomRepository extends JpaRepository<ReportRoom , Long> {
    List<ReportRoom> findByRoomId(long id);
    List<ReportRoom> findByReportId(long id);
}
