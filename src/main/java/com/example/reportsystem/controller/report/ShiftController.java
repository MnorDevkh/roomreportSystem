package com.example.reportsystem.controller.report;

import com.example.reportsystem.model.Shift;
import com.example.reportsystem.model.request.ShiftRequest;
import com.example.reportsystem.service.ShiftService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.repository.query.Param;
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
    public ResponseEntity<?> addNew(@RequestBody ShiftRequest shiftRequest){
        return service.save(shiftRequest);
    }
    @GetMapping("/current-user")
    public ResponseEntity<?> getByCurrentUser(@Param("pageNumber") Integer pageNumber,@Param("pageSize") Integer pageSize,@Param("sortBy") String sortBy,@Param("ascending") boolean ascending){
        return service.findShiftCurrenUser(pageNumber, pageSize, sortBy, ascending);
    }
    @DeleteMapping("/deleteById")
    public ResponseEntity<?> deleteById(Integer id){
        return service.deleteById(id);
    }
    @PutMapping("/updateById")
    public ResponseEntity<?> updateById(@Param("Shift ID") Integer id,@RequestBody ShiftRequest shiftRequest){
        return service.updateById(id,shiftRequest);
    }

}
