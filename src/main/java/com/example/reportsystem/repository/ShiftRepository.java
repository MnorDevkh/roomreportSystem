package com.example.reportsystem.repository;

import com.example.reportsystem.model.Shift;
import com.example.reportsystem.model.User;
import com.example.reportsystem.model.report.Report;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Integer> {

    Page<Shift> findByDeletedFalseAndUsers(PageRequest pageable, User currentUser);
    @Query("SELECT s FROM Shift s JOIN s.users u WHERE u.id = :userId")
    List<Shift> findByUserId(long userId);
}
