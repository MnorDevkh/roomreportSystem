package com.example.reportsystem.service.report.serviceimpl;

import com.example.reportsystem.model.report.Report;
import com.example.reportsystem.model.Shift;
import com.example.reportsystem.model.Subject;
import com.example.reportsystem.model.User;
import com.example.reportsystem.model.report.Room;
import com.example.reportsystem.model.request.ReportRequest;
import com.example.reportsystem.model.responses.ApiResponse;
import com.example.reportsystem.model.responses.ReportResponse;
import com.example.reportsystem.model.toDto.RoomDto;
import com.example.reportsystem.repository.*;
import com.example.reportsystem.repository.report.RoomRepository;
import com.example.reportsystem.service.report.ReportService;
import com.example.reportsystem.utilities.response.Message;
import com.example.reportsystem.utilities.response.ResponseObject;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReportServiceImp implements ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final ShiftRepository shiftRepository;
    private final RoomRepository roomRepository;
    private final SubjectRepository subjectRepository;

    ResponseObject res = new ResponseObject();
    Message message = new Message();


    @Override
    public ResponseEntity<?> findAllReport(Integer pageNumber, Integer pageSize, String sortBy, boolean ascending) {
        try {
            Sort.Direction sortDirection = ascending ? Sort.Direction.ASC : Sort.Direction.DESC;
            PageRequest pageable = PageRequest.of(pageNumber - 1, pageSize, sortDirection, sortBy);
            Page<Report> pageResult = reportRepository.findByDeletedFalse(pageable);
            List<ReportResponse> reportResponseList = new ArrayList<>();
            for (Report report : pageResult) {
                Optional<Report> reportOptional = reportRepository.findById(report.getId());
                if (reportOptional.isPresent()) {
                    ReportResponse reportResponse = reportFlag(reportOptional.get());
                    reportResponseList.add(reportResponse);
                    res.setStatus(true);
                    res.setMessage("fetch report" + reportOptional.get().getId() + " successfully");
                    res.setData(reportResponse);
                }
            }
            ApiResponse res = new ApiResponse(true, "Fetch books successful!", reportResponseList, pageResult.getNumber() + 1, pageResult.getSize(), pageResult.getTotalPages(), pageResult.getTotalElements());
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            // Log the exception or handle it more gracefully
            e.printStackTrace(); // or use a logging framework
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve reports.");
        }
    }

           @Override
        public ResponseEntity<?> findReportByTimeBetween(Integer pageNumber, Integer pageSize, String sortBy, boolean ascending, String startDateString, String endDateString) {
               try {
                   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                   Date startDate = dateFormat.parse(startDateString);
                   Date endDate = dateFormat.parse(endDateString);

                   LocalDate startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                   LocalDate endLocalDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


                   Sort.Direction sortDirection = ascending ? Sort.Direction.ASC : Sort.Direction.DESC;
                   PageRequest pageable = PageRequest.of(pageNumber - 1, pageSize, sortDirection, sortBy);
                   Page<Report> pageResult = reportRepository.findByDeletedFalseAndCreateDateBetween(startLocalDate, endLocalDate, pageable);

                   // Extract content from page result
                   List<Report> reports = pageResult.getContent();

                   // Transform reports into response objects
                   List<ReportResponse> reportResponseList = new ArrayList<>();
                   for (Report report : reports) {
                       ReportResponse reportResponse = reportFlag(report);
                       reportResponseList.add(reportResponse);
                   }

                   // Construct ApiResponse
                   ApiResponse res = new ApiResponse(true, "Fetch reports successful!", reportResponseList,
                           pageResult.getNumber() + 1, pageResult.getSize(),
                           pageResult.getTotalPages(), pageResult.getTotalElements());
                   return ResponseEntity.ok(res);
               } catch (Exception e) {
                   // Log the exception or handle it more gracefully
                   e.printStackTrace(); // or use a logging framework
                   return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve reports.");
               }
    }


    @Override
    public ResponseEntity<?> findByUserId(Integer id) {
//        try {
//            // Check if the user with the given id exists
//            Optional<User> userOptional = userRepository.findById(id);
//
//            return userOptional.map(user -> {
//                // User exists, proceed to fetch associated reports
//                List<Report> reportList = reportRepository.findByUser(user);
//
//                List<ReportResponse> reportResponseList = reportList.stream()
//                        .map(report -> ReportResponse.builder()
//                                .id(report.getId())
//                                .shift(report.getShift().toDto())
//                                .user(report.getUser().toDto())
//                                .subject(report.getSubject().toDto())
//                                .date(report.getDate())
//                                .studentNum(report.getStudentNum())
//                                .createDate(report.getCreatedate())
//                                .build())
//                        .collect(Collectors.toList());
//
//                // Return the ResponseEntity with the list of ReportResponse objects
//                ApiResponse apiResponse = ApiResponse.<List<ReportResponse>>builder()
//                        .payload(reportResponseList)
//                        .totalElement((long) reportResponseList.size())
//                        .build();
//
//                res.setStatus(true);
//                res.setMessage(message.getSuccess("Report"));
//                res.setData(apiResponse);
//                return ResponseEntity.ok(res);
//            }).orElse(ResponseEntity.notFound().build());
//
//        } catch (Exception e) {
//            // Handle exceptions appropriately, you might want to log the exception
//            e.printStackTrace(); // or use a logging framework
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
        return null;
    }

    @Override
    @Transactional
    public ResponseEntity<?> addNew(ReportRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = (User) authentication.getPrincipal();
            // Retrieve entities based on provided IDs
            Optional<Shift> shift = shiftRepository.findById(request.getShiftId());
            Optional<Subject> subject = subjectRepository.findById(request.getSubjectId());
            Set<Room> roomList = new HashSet<>();
            for (Integer roomId : request.getRoomIds()) {
                Optional<Room> room = roomRepository.findById(roomId);
                if (room.isPresent()) {
                    roomList.add(room.get());
                }
            }

            if (shift.isPresent() && subject.isPresent()) {
                // Create the report entity
                Report report = Report.builder()
                        .user(currentUser)
                        .shift(shift.get())
                        .rooms(roomList)
                        .subject(subject.get())
                        .reportDate(request.getDate())
                        .createDate(LocalDate.now())
                        .modifyDate(LocalDate.now())
                        .studentNum(request.getStudentNum())
                        .build();
                // Save the report
                reportRepository.save(report);
                ReportResponse reportResponse = reportFlag(report);

                return ResponseEntity.ok(reportResponse);
            } else {
                // Handle the case where any of the entities (user, shift, subject, or rooms) is not found
                return ResponseEntity.badRequest().body("Invalid request parameters.");
            }
        } catch (Exception e) {
            // Handle save operation failure
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save the report." + e);
        }
    }

    @Override
    public ResponseEntity<?> findReportCurrentUser(Integer pageNumber, Integer pageSize, String sortBy, boolean ascending) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = (User) authentication.getPrincipal();
            Sort.Direction sortDirection = ascending ? Sort.Direction.ASC : Sort.Direction.DESC;
            PageRequest pageable = PageRequest.of(pageNumber - 1, pageSize, sortDirection, sortBy);
            Page<Report> pageResult = reportRepository.findByDeletedFalseAndUser(pageable, currentUser);
            List<ReportResponse> reportResponseList = new ArrayList<>();
            for (Report report : pageResult) {
                Optional<Report> reportOptional = reportRepository.findById(report.getId());
                if (reportOptional.isPresent()) {
                    ReportResponse reportResponse = reportFlag(reportOptional.get());
                    reportResponseList.add(reportResponse);
                    res.setStatus(true);
                    res.setMessage("fetch report" + reportOptional.get().getId() + " successfully");
                    res.setData(reportResponse);
                }
            }
            ApiResponse res = new ApiResponse(true, "Fetch books successful!", reportResponseList, pageResult.getNumber() + 1, pageResult.getSize(), pageResult.getTotalPages(), pageResult.getTotalElements());
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            // Log the exception or handle it more gracefully
            e.printStackTrace(); // or use a logging framework
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve reports.");
        }
    }

    @Override
    public ResponseEntity<?> UpdateById(ReportRequest request, Integer id) {
        try {
            // Check if the lecturer with the given id exists
            Optional<Report> reportOptional = reportRepository.findById(id);
            System.out.println(reportOptional);
            if (reportOptional.isPresent()) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                User currentUser = (User) authentication.getPrincipal();
                // Retrieve entities based on provided IDs
                Optional<Shift> shift = shiftRepository.findById(request.getShiftId());
                Optional<Subject> subject = subjectRepository.findById(request.getSubjectId());
                Set<Room> roomList = new HashSet<>();
                for (Integer roomId : request.getRoomIds()) {
                    Optional<Room> room = roomRepository.findById(roomId);
                    if (room.isPresent()) {
                        roomList.add(room.get());
                    }
                }
                if (shift.isPresent() && subject.isPresent()) {
                    // Create the report entity
                    Report report = Report.builder()
                            .id(reportOptional.get().getId())
                            .user(currentUser)
                            .shift(shift.get())
                            .rooms(roomList)
                            .subject(subject.get())
                            .reportDate(reportOptional.get().getReportDate())
                            .createDate(reportOptional.get().getCreateDate())
                            .modifyDate(LocalDate.now())
                            .studentNum(request.getStudentNum())
                            .build();
                    // Save the report
                    reportRepository.save(report);
                    List<ReportResponse> reportResponseList = new ArrayList<>();
                    ReportResponse reportResponse = reportFlag(report);
                    reportResponseList.add(reportResponse);
                    // Return the ResponseEntity with the list of ReportResponse objects
                    res.setStatus(true);
                    res.setMessage(message.getSuccess("Report"));
                    res.setData(reportResponseList);
                } else {
                    // Lecturer with the given id not found, return a 404 Not Found response
                    res.setStatus(false);
                    res.setMessage(message.getFail("Report"));
                    res.setData(null);
                }
            }
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity<?> deleteById(Integer id) {
        try {
            Optional<Report> reportOptional = reportRepository.findById(id);
            if (reportOptional.isPresent()) {
                if (reportOptional.isPresent()) {
                    Report report = reportOptional.get();
                    report.setDeleted(true);
                        reportRepository.save(report);
                        List<ReportResponse> reportResponseList = new ArrayList<>();
                        ReportResponse reportResponse = reportFlag(report);
                        reportResponseList.add(reportResponse);
                        // Return the ResponseEntity with the list of ReportResponse objects
                        res.setStatus(true);
                        res.setMessage(message.getSuccess("Report"));
                        res.setData(reportResponseList);

                    }
            }
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<?> getReportById(Integer id) {
        Optional<Report> report = null;
        try {
            report = reportRepository.findById(id);
            if (report.isPresent()) {
                ReportResponse reportResponse = reportFlag(report.get());
                res.setStatus(true);
                res.setMessage("fetch report" + report.get().getId() + " successfully");
                res.setData(reportResponse);
            }
            return ResponseEntity.ok(res);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("fetch report" + report.get().getId() + "false");
        }

    }

    private RoomDto roomFlag(Room room) {
        return RoomDto.builder()
                .id(room.getId())
                .floor(room.getFloor())
                .type(room.getType())
                .name(room.getName())
                .description(room.getDescription())
                .build();
    }

    private ReportResponse reportFlag(Report report) {
        List<RoomDto> roomDtoList = new ArrayList<>();
        for (Room room : report.getRooms()) {
            RoomDto roomDto = roomFlag(room);
            roomDtoList.add(roomDto);
        }
        // Optionally, you can return a response with the saved report details
        return ReportResponse.builder()
                .id(report.getId())
                .shift(report.getShift().toDto())
                .user(report.getUser().toDto())
                .room(roomDtoList)
                .subject(report.getSubject().toDto())
                .reportDate(report.getReportDate())
                .createDate(report.getDeleteAtDate())
                .createDate(report.getReportDate())
                .studentNum(report.getStudentNum())
                .build();
    }


}
