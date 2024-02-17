package com.example.reportsystem.repository;

import com.example.reportsystem.model.UserSubject;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectToUserRepository extends JpaRepository<UserSubject,Long> {

    boolean existsByUserIdAndSubjectId(long user, long subject);

    void deleteByUserIdAndSubjectId(long user, long subject);
}
