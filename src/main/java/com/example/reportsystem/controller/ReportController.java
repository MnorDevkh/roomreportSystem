package com.example.reportsystem.controller;

import com.example.reportsystem.model.request.ReportRequest;
import com.example.reportsystem.service.ReportService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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
    public ResponseEntity<?> getAll(){
        return service.findAllReport();
    }
    @PostMapping("/add")
    public ResponseEntity<?> save(@RequestBody ReportRequest request){
        return service.addNew(request);
    }
    @GetMapping("/byTeacher")
    public ResponseEntity<?> getByUser(@Param("User id") Long id){
        return service.findByUserId(id);
    }@GetMapping("/by-currenUser")
    public ResponseEntity<?> getByUser(){
        return service.getReportCurrentUser();
    }
    @PutMapping("/updateById")
    public  ResponseEntity<?> updateById(@Param("report id") long id,@RequestBody ReportRequest reportRequest){
        return service.UpdateById(reportRequest,id);
    }
    @DeleteMapping("/deleteById")
    public ResponseEntity<?> deleteById(@Param("Report id") long id){
        return service.deleteById(id);
    }
    @GetMapping("/getReportById")
    public ResponseEntity<?> getById(@Param("Report Id") long id){
        return service.getReportById(id);
    }
}
