package com.example.reportsystem.controller;

import com.example.reportsystem.model.request.UserShiftRequest;
import com.example.reportsystem.service.UserShiftService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("api/v1/user-shift")
public class UserShiftController {
    private final UserShiftService userShiftService;
    @PostMapping("/add")
   public ResponseEntity<?> addUserShift(@RequestBody UserShiftRequest userShiftRequest){
        return userShiftService.save(userShiftRequest);
    }
    @DeleteMapping("/deleteById")
    public ResponseEntity<?> delete(@Param("User id") long id){
        return userShiftService.deleteById(id);
    }
}
