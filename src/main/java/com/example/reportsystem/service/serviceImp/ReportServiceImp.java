package com.example.reportsystem.service.serviceImp;

import com.example.reportsystem.model.*;
import com.example.reportsystem.model.request.ReportRequest;
import com.example.reportsystem.model.responses.ApiResponse;
import com.example.reportsystem.model.responses.PageResponse;
import com.example.reportsystem.model.responses.ReportResponse;
import com.example.reportsystem.model.responses.RoomResponse;
import com.example.reportsystem.model.toDto.RoomDto;
import com.example.reportsystem.repository.*;
import com.example.reportsystem.service.ReportService;
import com.example.reportsystem.utilities.response.Message;
import com.example.reportsystem.utilities.response.ResponseObject;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.swing.plaf.IconUIResource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImp implements ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final ShiftRepository shiftRepository;
    private final RoomRepository roomRepository;
    private final SubjectRepository subjectRepository;
    private final ReportRoomRepository reportRoomRepository;

    ResponseObject res = new ResponseObject();
    Message message = new Message();



    @Override
    public ResponseEntity<?> findAllReport() {
        try {
            List<Report> reports = reportRepository.findAll();
            List<ReportResponse> reportResponseList = new ArrayList<>();
            for(Report report: reports){
                Optional<Shift> shift = shiftRepository.findById(report.getShift().getId());
                Optional<Subject> subject = subjectRepository.findById(report.getSubject().getId());
                Optional<User> user = userRepository.findById(report.getUser().getId());
                List<ReportRoom> reportRoom = reportRoomRepository.findByReportId(report.getId());
                List<RoomDto>  roomList = new ArrayList<>();
                for(ReportRoom room :reportRoom){
                    Optional<Room> room1 = roomRepository.findById(room.getRoom().getId());
                    if (room1.isPresent()){
                        RoomDto roomDto = RoomDto.builder()
                                .id(room1.get().getId())
                                .name(room1.get().getName())
                                .floor(room1.get().getFloor())
                                .type(room1.get().getType())
                                .description(room1.get().getDescription())
                                .build();
                        roomList.add(roomDto);
                    }
                }
                if(shift.isPresent() && subject.isPresent() && !report.isStatus()){
                    ReportResponse reportResponse = ReportResponse.builder()
                            .id(report.getId())
                            .shift(shift.get().toDto())
                            .subject(subject.get().toDto())
                            .user(user.get().toDto())
                            .room(roomList)
                            .studentNum(report.getStudentNum())
                            .date(report.getDate())
                            .createDate(report.getCreatedate())
                            .build();
                    reportResponseList.add(reportResponse);
            }
                System.out.println("reportResponseList" + reportResponseList);

            }
            ApiResponse apiResponse = ApiResponse.builder()
                    .payload(reportResponseList)
                    .totalElement((long) reportResponseList.size())
                    .build();
            res.setStatus(true);
            res.setMessage(message.getSuccess("Report"));
            res.setData(apiResponse);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            // Log the exception or handle it more gracefully
            e.printStackTrace(); // or use a logging framework
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve reports.");
        }
    }


    @Override
    public ResponseEntity<?> findByUserId(Long id) {
        try {
            // Check if the user with the given id exists
            Optional<User> userOptional = userRepository.findById(id);

            return userOptional.map(user -> {
                // User exists, proceed to fetch associated reports
                List<Report> reportList = reportRepository.findByUser(user);

                List<ReportResponse> reportResponseList = reportList.stream()
                        .map(report -> ReportResponse.builder()
                                .id(report.getId())
                                .shift(report.getShift().toDto())
                                .user(report.getUser().toDto())
                                .subject(report.getSubject().toDto())
                                .date(report.getDate())
                                .studentNum(report.getStudentNum())
                                .createDate(report.getCreatedate())
                                .build())
                        .collect(Collectors.toList());

                // Return the ResponseEntity with the list of ReportResponse objects
                ApiResponse apiResponse = ApiResponse.<List<ReportResponse>>builder()
                        .payload(reportResponseList)
                        .totalElement((long) reportResponseList.size())
                        .build();

                res.setStatus(true);
                res.setMessage(message.getSuccess("Report"));
                res.setData(apiResponse);
                return ResponseEntity.ok(res);
            }).orElse(ResponseEntity.notFound().build());

        } catch (Exception e) {
            // Handle exceptions appropriately, you might want to log the exception
            e.printStackTrace(); // or use a logging framework
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @Override
    @Transactional
    public ResponseEntity<?> addNew(ReportRequest request) {
        try {
            // Retrieve entities based on provided IDs
            Optional<User> user = userRepository.findById(request.getUserId());
            Optional<Shift> shift = shiftRepository.findById(request.getShiftId());
            Optional<Subject> subject = subjectRepository.findById(request.getSubjectId());

            if (user.isPresent() && shift.isPresent() && subject.isPresent()) {
                // Create the report entity
                Report report = Report.builder()
                        .user(user.get())
                        .shift(shift.get())
                        .subject(subject.get())
                        .date(request.getDate())
                        .createdate(LocalDate.now())
                        .studentNum(request.getStudentNum())
                        .build();
                // Save the report
                reportRepository.save(report);
                    for(Long room: request.getRoomIds()){
                        Optional<Room> roomOptional = roomRepository.findById(room);
                        if (roomOptional.isPresent()){
                            ReportRoom reportRoom = ReportRoom.builder()
                                    .room(roomOptional.get())
                                    .report(report)
                                    .build();
                            reportRoomRepository.save(reportRoom);
                        }
                    }
                List<ReportRoom> reportRoom = reportRoomRepository.findByReportId(report.getId());
                List<RoomDto>  roomList = new ArrayList<>();
                for(ReportRoom room :reportRoom){
                    Optional<Room> room1 = roomRepository.findById(room.getRoom().getId());
                    if (room1.isPresent()){
                        RoomDto roomDto = RoomDto.builder()
                                .id(room1.get().getId())
                                .name(room1.get().getName())
                                .floor(room1.get().getFloor())
                                .type(room1.get().getType())
                                .description(room1.get().getDescription())
                                .build();
                        roomList.add(roomDto);
                    }
                }
                // Optionally, you can return a response with the saved report details
                ReportResponse reportResponse = ReportResponse.builder()
                        .shift(shift.get().toDto())
                        .user(user.get().toDto())
                        .room(roomList)
                        .date(request.getDate())
                        .createDate(LocalDate.now())
                        .studentNum(request.getStudentNum())
                        .build();
                return ResponseEntity.ok(reportResponse);
            } else {
                // Handle the case where any of the entities (user, shift, subject, or rooms) is not found
                return ResponseEntity.badRequest().body("Invalid request parameters.");
            }
        } catch (Exception e) {
            // Handle save operation failure
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save the report.");
        }
    }

    @Override
    public ResponseEntity<?> getReportCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        try {
            // Check if the lecturer with the given id exists
            Optional<User> userOptional = userRepository.findById(currentUser.getId());
            if (userOptional.isPresent()) {
                // Lecturer exists, proceed to fetch associated reports
                User user = userOptional.get();
                // Assuming there is a method in your ReportRepository to find reports by lecturer
                List<Report> reportList = reportRepository.findByUser(user);
                List<ReportResponse> reportResponseList = new ArrayList<>();
                for(Report report: reportList){
                    if(!report.isStatus()){
                    List<ReportRoom> reportRoom = reportRoomRepository.findByReportId(report.getId());
                    List<RoomDto>  roomList = new ArrayList<>();
                    for(ReportRoom room :reportRoom){
                        Optional<Room> room1 = roomRepository.findById(room.getRoom().getId());
                        if (room1.isPresent()){
                            RoomDto roomDto = RoomDto.builder()
                                    .id(room1.get().getId())
                                    .name(room1.get().getName())
                                    .floor(room1.get().getFloor())
                                    .type(room1.get().getType())
                                    .description(room1.get().getDescription())
                                    .build();
                            roomList.add(roomDto);
                            System.out.println("roomDto"+ roomDto);
                        }
                    }
                    System.out.println("roomList"+ roomList);

                        ReportResponse reportResponse = ReportResponse.builder()
                                .id(report.getId())
                                .shift(report.getShift().toDto())
                                .user(report.getUser().toDto())
                                .subject(report.getSubject().toDto())
                                .room(roomList)
                                .date(report.getDate())
                                .studentNum(report.getStudentNum())
                                .createDate(report.getCreatedate())
                                .build();
                        reportResponseList.add(reportResponse);
                    }

                }
                // Return the ResponseEntity with the list of ReportResponse objects
                res.setStatus(true);
                res.setMessage(message.getSuccess("Report"));
                res.setData(reportResponseList);
                return ResponseEntity.ok(res);

            } else {
                // Lecturer with the given id not found, return a 404 Not Found response
                return ResponseEntity.notFound().build();
            }
        }catch (Exception e){
            // Handle exceptions appropriately, you might want to log the exception
            e.printStackTrace(); // or use a logging framework
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<?> UpdateById(ReportRequest request,Long id) {
        try {
            // Check if the lecturer with the given id exists
            Optional<Report> reportOptional = reportRepository.findById(id);
            System.out.println(reportOptional);
            if (reportOptional.isPresent()) {
                // Lecturer exists, proceed to fetch associated reports
                Optional<Shift> shift = shiftRepository.findById(request.getShiftId());
                Optional<User> user = userRepository.findById(request.getUserId());
                Optional<Subject> subject = subjectRepository.findById(request.getSubjectId());
                Report reportObj = null;
                if (shift.isPresent() && user.isPresent()) {
                    reportObj = Report.builder()
                            .id(reportOptional.get().getId())
                            .shift(shift.get())
                            .user(user.get())
                            .subject(subject.get())
                            .date(request.getDate())
                            .createdate(LocalDate.now())
                            .studentNum(request.getStudentNum())
                            .build();
                }
                Report report = reportRepository.save(reportObj);

//                if(!reportRoom2.isEmpty()){
                for(Long room : request.getRoomIds()){
                    Optional<Room> roomOptional = roomRepository.findById(room);
                    List<ReportRoom> reportRoom2 = reportRoomRepository.findByReportId(report.getId());
                    for (ReportRoom reportRoom3 : reportRoom2){
                        Optional<ReportRoom> reportRoom5 = reportRoomRepository.findById(reportRoom3.getId());
                        reportRoomRepository.deleteById(reportRoom5.get().getId());
                    }
                }
                    for (Long room : request.getRoomIds()) {
                        Optional<Room> roomOptional = roomRepository.findById(room);
                        if (roomOptional.isPresent()) {
                            ReportRoom reportRoom6 = ReportRoom.builder()
                                    .report(report)
                                    .room(roomOptional.get())
                                    .build();
                            reportRoomRepository.save(reportRoom6);
                        }
                    }

                // Assuming there is a method in your ReportRepository to find reports by lecturer
                List<ReportRoom> reportRoom = reportRoomRepository.findByReportId(reportOptional.get().getId());
                List<RoomDto>  roomList = new ArrayList<>();
                for(ReportRoom room :reportRoom){
                    Optional<Room> room1 = roomRepository.findById(room.getRoom().getId());
                    if (room1.isPresent()){
                        RoomDto roomDto = RoomDto.builder()
                                .id(room1.get().getId())
                                .name(room1.get().getName())
                                .floor(room1.get().getFloor())
                                .type(room1.get().getType())
                                .description(room1.get().getDescription())
                                .build();
                        roomList.add(roomDto);
                    }
                }
                List<ReportResponse> reportResponseList = new ArrayList<>();
                    ReportResponse reportResponse = ReportResponse.builder()
                            .id(report.getId())
                            .shift(report.getShift().toDto())
                            .user(report.getUser().toDto())
                            .subject(report.getSubject().toDto())
                            .room(roomList)
                            .date(report.getDate())
                            .studentNum(report.getStudentNum())
                            .createDate(report.getCreatedate())
                            .build();
                    reportResponseList.add(reportResponse);
                // Return the ResponseEntity with the list of ReportResponse objects
                res.setStatus(true);
                res.setMessage(message.getSuccess("Report"));
                res.setData(reportResponseList);
                return ResponseEntity.ok(res);
            } else {
                // Lecturer with the given id not found, return a 404 Not Found response
                return ResponseEntity.notFound().build();
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity<?> deleteById(long id) {
        try {
            Optional<Report> reportOptional = reportRepository.findById(id);
            if (reportOptional.isPresent()) {
                Optional<Shift> shift = shiftRepository.findById(reportOptional.get().getShift().getId());
                Optional<User> user = userRepository.findById(reportOptional.get().getUser().getId());
                Optional<Subject> subject = subjectRepository.findById(reportOptional.get().getSubject().getId());
                List<ReportRoom> reportRoom = reportRoomRepository.findByReportId(reportOptional.get().getId());
                List<RoomDto>  roomList = new ArrayList<>();
                for(ReportRoom room :reportRoom){
                    Optional<Room> room1 = roomRepository.findById(room.getRoom().getId());
                    if (room1.isPresent()){
                        RoomDto roomDto = RoomDto.builder()
                                .id(room1.get().getId())
                                .name(room1.get().getName())
                                .floor(room1.get().getFloor())
                                .type(room1.get().getType())
                                .description(room1.get().getDescription())
                                .build();
                        roomList.add(roomDto);
                    }
                }
                Report report = Report.builder()
                        .id(reportOptional.get().getId())
                        .shift(shift.get())
                        .user(user.get())
                        .subject(subject.get())
                        .date(reportOptional.get().getDate())
                        .createdate(LocalDate.now())
                        .studentNum(reportOptional.get().getStudentNum())
                        .status(true)
                        .deleteAtDate(LocalDate.now())
                        .build();
                reportRepository.save(report);
                res.setStatus(true);
                res.setMessage(message.getSuccess("Report deleted successfully"));
                return ResponseEntity.ok(res);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<?> getReportById(long id) {
        Optional<Report> report = reportRepository.findById(id);

        List<ReportRoom> reportRoom = reportRoomRepository.findByReportId(report.get().getId());
        List<RoomDto>  roomList = new ArrayList<>();
        for(ReportRoom room :reportRoom){
            Optional<Room> room1 = roomRepository.findById(room.getRoom().getId());
            if (room1.isPresent()){
                RoomDto roomDto = RoomDto.builder()
                        .id(room1.get().getId())
                        .name(room1.get().getName())
                        .floor(room1.get().getFloor())
                        .type(room1.get().getType())
                        .description(room1.get().getDescription())
                        .build();
                roomList.add(roomDto);
            }
        }
        try {
        if(report.isPresent()){
            ReportResponse reportResponse = ReportResponse.builder()
                    .id(report.get().getId())
                    .shift(report.get().getShift().toDto())
                    .user(report.get().getUser().toDto())
                    .subject(report.get().getSubject().toDto())
                    .room(roomList)
                    .date(report.get().getDate())
                    .studentNum(report.get().getStudentNum())
                    .createDate(report.get().getCreatedate())
                    .build();
            res.setStatus(true);
            res.setMessage("fetch report" + report.get().getId() + " successfully");
            res.setData(reportResponse);
        }
            return ResponseEntity.ok(res);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("fetch report" + report.get().getId() + "false");
        }

    }

}
