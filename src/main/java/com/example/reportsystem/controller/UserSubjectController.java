package com.example.reportsystem.controller;


import com.example.reportsystem.model.request.UserSubjectRequest;
import com.example.reportsystem.service.UserSubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("api/v1/user-subject")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class UserSubjectController {
    private final UserSubjectService userSubjectService;



    @PostMapping("/add")
    @Operation(summary = "add subject to User")
    public ResponseEntity<?> add(@RequestBody UserSubjectRequest userSubjectRequest){
        return userSubjectService.save(userSubjectRequest);
    }
    @DeleteMapping("/deleteById")
    public ResponseEntity<?> delete(@Param("User id") long id){
        return userSubjectService.deleteById(id);
    }
}
