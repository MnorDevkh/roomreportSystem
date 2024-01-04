package com.example.reportsystem.controller;

import com.example.reportsystem.model.Shift;
import com.example.reportsystem.model.request.ShiftRequest;
import com.example.reportsystem.service.ShiftService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api/v1/shift")
@SecurityRequirement(name = "bearerAuth")
public class ShiftController {
    private final ShiftService service;

    public ShiftController(ShiftService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        return service.findAll();
    };
    @PostMapping("/add")
    public ResponseEntity<?> addNew(ShiftRequest shiftRequest){
        return service.save(shiftRequest);
    }
}
