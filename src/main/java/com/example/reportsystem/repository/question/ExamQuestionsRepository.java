package com.example.reportsystem.repository.question;


import com.example.reportsystem.model.question.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamQuestionsRepository extends JpaRepository<Questions,Integer > {
}
