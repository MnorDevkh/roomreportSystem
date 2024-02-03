//package com.example.reportsystem.controller;
//
//import com.example.reportsystem.model.request.TeacherRequest;
//import com.example.reportsystem.service.LecturerService;
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@CrossOrigin
//@RequestMapping("api/v1/teachers")
//@SecurityRequirement(name = "bearerAuth")
//public class TeacherController {
//    private final LecturerService lecturerService;
//    public TeacherController(LecturerService lecturerService) {
//        this.lecturerService = lecturerService;
//    }
//    @GetMapping("/all")
//    public ResponseEntity<?> getAll() {
//        return lecturerService.findAllTeacher();
//    }
//    @PostMapping("/all")
//    public ResponseEntity<?> add(TeacherRequest teacherRequest){
//        return lecturerService.save(teacherRequest);
//    }
//}
