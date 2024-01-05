package com.example.reportsystem.service.serviceImp;

import com.example.reportsystem.model.Report;
import com.example.reportsystem.model.Room;
import com.example.reportsystem.model.Shift;
import com.example.reportsystem.model.Teacher;
import com.example.reportsystem.model.request.ReportRequest;
import com.example.reportsystem.model.responses.ApiResponse;
import com.example.reportsystem.model.responses.ReportResponse;
import com.example.reportsystem.repository.ReportRepository;
import com.example.reportsystem.repository.RoomRepository;
import com.example.reportsystem.repository.ShiftRepository;
import com.example.reportsystem.repository.TeacherRepository;
import com.example.reportsystem.service.ReportService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportServiceImp implements ReportService {
    private final ReportRepository reportRepository;
    private final TeacherRepository teacherRepository;
    private final ShiftRepository shiftRepository;
    private final RoomRepository roomRepository;

    public ReportServiceImp(ReportRepository reportRepository, TeacherRepository teacherRepository, ShiftRepository shiftRepository, RoomRepository roomRepository) {
        this.reportRepository = reportRepository;
        this.teacherRepository = teacherRepository;
        this.shiftRepository = shiftRepository;
        this.roomRepository = roomRepository;
    }


    @Override
    public ResponseEntity<?> findAllReport(Integer size,Integer page) {
        int offset = (page - 1) * size;

        try {
            // Create a PageRequest for pagination
            PageRequest pageRequest = PageRequest.of(page - 1, size);

            // Retrieve a page of reports from the repository
            Page<Report> reportPage = reportRepository.findAll(pageRequest);
            int element;
            if(size > reportPage.getTotalElements()){
                element = (int) reportPage.getTotalElements();
            }else {
                element = size;
            }
            List<ReportResponse> reportResponseList = new ArrayList<>();
            for (Report report : reportPage.getContent()) {
                ReportResponse reportResponse = ReportResponse.builder()
                        .id(report.getId())
                        .shift(report.getShift().toDto())
                        .teacher(report.getTeacher().toDto())
                        .room(report.getRoom().toDto())
                        .date(report.getDate())
                        .studentNum(report.getStudentNum())
                        .build();
                reportResponseList.add(reportResponse);
            }

            return ResponseEntity.ok(ApiResponse.<List<ReportResponse>>builder()
                    .message("Success: Reports found")
                    .status(HttpStatus.OK)
                    .payload(reportResponseList)
                    .page(page)
                    .size(element)
                    .totalElement(reportPage.getTotalElements())
                    .totalPages(reportPage.getTotalPages())
                    .build());



        } catch (Exception e) {
            // Handle exceptions appropriately, e.g., log the error and return an error response.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<?> findByTeacherId(Integer id) {
        try {
            // Check if the teacher with the given id exists
            Optional<Teacher> teacherOptional = teacherRepository.findById(id);
        if (teacherOptional.isPresent()) {
            // Teacher exists, proceed to fetch associated reports
            Teacher teacher = teacherOptional.get();

            // Assuming there is a method in your ReportRepository to find reports by teacher
            List<Report> reportList = reportRepository.findByTeacher(teacher);

            List<ReportResponse> reportResponseList = new ArrayList<>();
            for(Report report: reportList){
                ReportResponse reportResponse = ReportResponse.builder()
                        .id(report.getId())
                        .shift(report.getShift().toDto())
                        .teacher(report.getTeacher().toDto())
                        .room(report.getRoom().toDto())
                        .date(report.getDate())
                        .studentNum(report.getStudentNum())
                        .build();
                reportResponseList.add(reportResponse);
            }

            // Return the ResponseEntity with the list of ReportResponse objects
            return ResponseEntity.ok(ApiResponse.builder()
                            .message("report fond")
                            .status(HttpStatus.OK)
                            .payload(reportResponseList)
                            .totalElement(reportResponseList.size())
                    .build());
        } else {
            // Teacher with the given id not found, return a 404 Not Found response
            return ResponseEntity.notFound().build();
        }
        }catch (Exception e){
            // Handle exceptions appropriately, you might want to log the exception
            e.printStackTrace(); // or use a logging framework
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @Override
    public ResponseEntity<?> addNew(ReportRequest request) {
        Optional<Room> room = roomRepository.findById(request.getRoomId());
        Optional<Shift> shift = shiftRepository.findById(request.getShiftId());
        Optional<Teacher> teacher = teacherRepository.findById(request.getTeacherId());
        Report reportObj = null;
        if (room.isPresent() && shift.isPresent() && teacher.isPresent()) {
            reportObj = Report.builder()
                .shift(shift.get())
                .teacher(teacher.get())
                .room(room.get())
                .date(request.getDate())
                .createdAt(LocalDate.now())
                .studentNum(request.getStudentNum())
                .build();
        }


        try {
            // Save the report
            Report savedReport = reportRepository.save(reportObj);
            // Return a success response with the saved report details
            return ResponseEntity.ok(savedReport);
        } catch (Exception e) {
            // Handle save operation failure
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
