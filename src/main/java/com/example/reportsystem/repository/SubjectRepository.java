package com.example.reportsystem.repository;

import com.example.reportsystem.model.Subject;
import com.example.reportsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,Integer>{
    List<Subject> findByDeletedFalseAndUsers(User currentUser);

}
