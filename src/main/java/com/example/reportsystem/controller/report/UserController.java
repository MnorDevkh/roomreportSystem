package com.example.reportsystem.controller.report;

import com.example.reportsystem.model.request.UserRequest;
import com.example.reportsystem.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> updateUser(@Param("User id") long id, @RequestBody UserRequest userRequest){
        return userService.AddSubjectRoomShiftToLecturer(id,userRequest);
    }

}
