package com.example.reportsystem.controller;

import com.example.reportsystem.model.request.RoomRequest;
import com.example.reportsystem.service.RoomService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/v1/room")
@SecurityRequirement(name = "bearerAuth")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
    return roomService.findAll();
}
    @PostMapping("/add")
    public ResponseEntity<?> addNew(RoomRequest request){
        return roomService.save(request);
    }
}
