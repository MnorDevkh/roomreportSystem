package com.example.reportsystem.controller.report;

import com.example.reportsystem.model.request.ReportRequest;
import com.example.reportsystem.service.report.ReportService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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
    public ResponseEntity<?> getAll(@Param("pageNumber") Integer pageNumber,@Param("pageSize") Integer pageSize,@Param("sortBy") String sortBy,@Param("ascending") boolean ascending){
        return service.findAllReport( pageNumber,  pageSize,  sortBy,  ascending);
    }
    @GetMapping("/getByBetweenDate")
    public ResponseEntity<?> getByBetweenDate(@Param("pageNumber") Integer pageNumber, @Param("pageSize") Integer pageSize, @Param("sortBy") String sortBy, @Param("ascending") boolean ascending , @Param("startDate") String startDate, @Param("endDate") String endDate){

        return service.findReportByTimeBetween( pageNumber,  pageSize,  sortBy,  ascending, startDate,endDate);
    }
    @PostMapping("/add")
    public ResponseEntity<?> save(@RequestBody ReportRequest request){
        return service.addNew(request);
    }
    @GetMapping("/byTeacher")
    public ResponseEntity<?> getByUser(@Param("User id") Integer id){
        return service.findByUserId(id);
    }@GetMapping("/by-currenUser")
    public ResponseEntity<?> getByUser(@Param("pageNumber") Integer pageNumber,@Param("pageSize") Integer pageSize,@Param("sortBy") String sortBy,@Param("ascending") boolean ascending){
        return service.findReportCurrentUser(pageNumber,pageSize, sortBy, ascending);
    }
    @PutMapping("/updateById")
    public  ResponseEntity<?> updateById(@Param("report id") Integer id,@RequestBody ReportRequest reportRequest){
        return service.UpdateById(reportRequest,id);
    }
    @DeleteMapping("/deleteById")
    public ResponseEntity<?> deleteById(@Param("Report id") Integer id){
        return service.deleteById(id);
    }
    @GetMapping("/getReportById")
    public ResponseEntity<?> getById(@Param("Report Id") Integer id){
        return service.getReportById(id);
    }
}
