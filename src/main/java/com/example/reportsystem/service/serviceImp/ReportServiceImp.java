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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<?> findAllReport() {
        try {
            List<Report> reportList = reportRepository.findAll();
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
                System.out.println("reports" + reportList);

            }
            System.out.println("reportResponseList" + reportResponseList);
            return ResponseEntity.ok(ApiResponse.<List<ReportResponse>>builder()
                    .message("Success: Reports found")
                    .status(HttpStatus.OK)
                    .payload(reportResponseList)
                    .build());


        } catch (Exception e) {
            // Handle exceptions appropriately, e.g., log the error and return an error response.
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
