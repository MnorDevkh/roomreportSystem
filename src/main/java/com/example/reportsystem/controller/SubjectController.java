package com.example.reportsystem.controller;

import com.example.reportsystem.model.User;
import com.example.reportsystem.model.request.ShiftRequest;
import com.example.reportsystem.model.request.SubjectRequest;
import com.example.reportsystem.service.SubjectService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api/v1/subject")
@SecurityRequirement(name = "bearerAuth")
public class SubjectController {
    private final SubjectService service;
    public SubjectController(SubjectService service) {
        this.service = service;
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        return service.findAllSubject();
    }
    @PostMapping("/add")
    public ResponseEntity<?> saveSubject(@RequestBody SubjectRequest subjectRequest){
        return  service.addSubject(subjectRequest);
    }
    @GetMapping("/by-user")
    public ResponseEntity<?> getByUser(@Param("User Id") long id){
        return service.findSubjectByUser(id);
    }
    @GetMapping("/current-user")
    public ResponseEntity<?> getAllByCurrent(){
        return service.findSubject();
    }
    @DeleteMapping("/deleteById")
    public ResponseEntity<?> deleteById(long id){
        return service.deleteById(id);
    }
    @PutMapping("/updateById")
    public  ResponseEntity<?> updateById(@Param("Subject id") long id,@RequestBody SubjectRequest subjectRequest){
        return service.updateById(id,subjectRequest);
    }
    @GetMapping("/test")
    public  String test(){
        return "hello";
    }
}
