package com.example.reportsystem.controller;

import com.example.reportsystem.model.request.TeacherRequest;
import com.example.reportsystem.service.TeacherService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1/teachers")
@SecurityRequirement(name = "bearerAuth")
public class TeacherController {
    private final TeacherService teacherService;
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return teacherService.findAllTeacher();
    }
    @PostMapping("/all")
    public ResponseEntity<?> add(TeacherRequest teacherRequest){
        return teacherService.save(teacherRequest);
    }
}
