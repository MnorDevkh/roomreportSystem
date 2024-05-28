package com.example.reportsystem.controller.question;


import com.example.reportsystem.model.question.request.QuestionsRequest;
import com.example.reportsystem.service.question.ExamQuestionsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/exam_duc")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@SecurityRequirement(name = "bearerAuth")
public class QuestionController {

    private final ExamQuestionsService examQuestionsService;

    public QuestionController(ExamQuestionsService examQuestionsService) {
        this.examQuestionsService = examQuestionsService;
    }

    @PostMapping("/add")
    ResponseEntity<?> save(@RequestBody QuestionsRequest questionsRequest){
        System.out.println(" 12131");
        return examQuestionsService.add(questionsRequest);
//        return null;
    }
    @GetMapping("/getAll")
    ResponseEntity<?> getAll(){

//        return examQuestionsService.findAll();
        return null;
    }



}
