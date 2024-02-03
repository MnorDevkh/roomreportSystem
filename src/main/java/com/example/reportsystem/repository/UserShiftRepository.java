package com.example.reportsystem.repository;

import com.example.reportsystem.model.User;
import com.example.reportsystem.model.UserShift;
import com.example.reportsystem.model.UserSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserShiftRepository extends JpaRepository<UserShift,Long> {
    List<UserShift> findAllByUser(User user);
    List<UserShift> findAllByShiftId(long id);
    List<UserShift> findAllByUserId(long id);

    boolean existsByUserIdAndShiftId(long user, long shift);
}
