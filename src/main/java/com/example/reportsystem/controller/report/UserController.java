package com.example.reportsystem.controller.report;

import com.example.reportsystem.model.request.LectureRequest;
import com.example.reportsystem.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1/user")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/currentUser")
    public  ResponseEntity<?> getCurrentUser() {
        return  userService.findUser();
    }
    @GetMapping("/by-subject")
    public ResponseEntity<?> getUserBySubject(long id){
        return userService.findUserBySubject(id);
    }
    @GetMapping("/by-shift")
    public ResponseEntity<?> findUserByShift(long id){
        return userService.findUserByShift(id);
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllUser(){
        return userService.findAllUser();
    }
    @GetMapping("/getById")
    public ResponseEntity<?> getUserById(long id){
        return userService.findUserById(id);
    }
    @PutMapping("update")
    public ResponseEntity<?> updateUser(@RequestBody LectureRequest lectureRequest){
        return userService.AddSubjectRoomShiftToLecturer(lectureRequest);
    }

}
