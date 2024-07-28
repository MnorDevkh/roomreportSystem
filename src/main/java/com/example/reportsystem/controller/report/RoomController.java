package com.example.reportsystem.controller.report;

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
    @GetMapping("/getById")
    public ResponseEntity<?> getById(@Param("Room id") Integer id){
        return roomService.findById(id);
    }
    @PostMapping("/add")
    public ResponseEntity<?> addNew(@RequestBody RoomRequest request){
        return roomService.save(request);
    }
    @DeleteMapping("/deleteById")
    public ResponseEntity<?> deleteById(@Param("Room id") Integer id) {
        return roomService.deleteById(id);
    }
    @PutMapping("/updetById")
    public ResponseEntity<?> updateById(@RequestBody RoomRequest request , @Param("room id") Integer id){
        return roomService.updateById(request, id);
    }
}
