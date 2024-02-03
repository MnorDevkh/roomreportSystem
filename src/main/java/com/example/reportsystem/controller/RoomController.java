package com.example.reportsystem.controller;

import com.example.reportsystem.model.request.RoomRequest;
import com.example.reportsystem.service.RoomService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.repository.query.Param;
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
    public ResponseEntity<?> addNew(@RequestBody RoomRequest request){
        return roomService.save(request);
    }
    @DeleteMapping("/deleteById")
    public ResponseEntity<?> deleteById(@Param("Room id") long id) {

        return roomService.deleteById(id);
    }
    @PutMapping("/updetById")
    public ResponseEntity<?> updateById(@RequestBody RoomRequest request , @Param("room id") long id){
        return roomService.updateById(request, id);
    }
}
