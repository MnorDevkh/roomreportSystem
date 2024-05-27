package com.example.reportsystem.repository.report;

import com.example.reportsystem.model.report.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room , Integer> {
}
