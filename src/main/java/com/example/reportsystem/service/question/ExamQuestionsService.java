package com.example.reportsystem.service.question;


import com.example.reportsystem.model.question.request.QuestionsRequest;
import com.example.reportsystem.model.question.response.QuestionResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ExamQuestionsService {
    ResponseEntity<QuestionResponse> add(QuestionsRequest questionsRequest);
    ResponseEntity<List<QuestionResponse>> findAll();
}
