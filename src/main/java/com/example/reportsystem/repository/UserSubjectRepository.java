package com.example.reportsystem.repository;

import com.example.reportsystem.model.Lecturer;
import com.example.reportsystem.model.LecturerShift;
import com.example.reportsystem.model.User;
import com.example.reportsystem.model.UserSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSubjectRepository extends JpaRepository<UserSubject, Long> {
    List<UserSubject> findAllByUser(User user);
    List<UserSubject> findAllByUserId(long id);
    List<UserSubject> findAllBySubjectId(long id);
}
