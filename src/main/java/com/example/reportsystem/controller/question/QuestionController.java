package com.example.reportsystem.controller.question;


import com.example.reportsystem.model.question.request.QuestionsRequest;
import com.example.reportsystem.service.question.ExamQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("exam_duc")
public class QuestionController {
    @Autowired
    ExamQuestionsService examQuestionsService;
    @PostMapping("/add")
    ResponseEntity<?> save(@RequestBody QuestionsRequest questionsRequest){
        return examQuestionsService.add(questionsRequest);
    }
    @GetMapping("getAll")
    ResponseEntity<?> getAll(){
        return examQuestionsService.findAll();
    }

}
