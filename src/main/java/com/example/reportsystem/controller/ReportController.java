package com.example.reportsystem.controller;

import com.example.reportsystem.model.request.ReportRequest;
import com.example.reportsystem.service.ReportService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin
@RestController
@RequestMapping("api/v1/report")
@SecurityRequirement(name = "bearerAuth")
public class ReportController {

    public final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAll(@RequestParam(name = "page", required = false) Integer page,
                                    @RequestParam(name = "size", required = false) Integer size) {
        return service.findAllReport(size, page);
    }
    @PostMapping("/add")
    public ResponseEntity<?> save(@RequestBody ReportRequest request){
        return service.addNew(request);
    }
    @GetMapping("/byTeacher")
    public ResponseEntity<?> getByTeacher(@Param("teacher id") Integer id){
        return service.findByTeacherId(id);
    }
}
