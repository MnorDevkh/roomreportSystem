//package com.example.reportsystem.service.serviceImp;
//
//import com.example.reportsystem.model.Lecturer;
//import com.example.reportsystem.model.LecturerShift;
//import com.example.reportsystem.model.Shift;
//import com.example.reportsystem.model.request.UserRequest;
//import com.example.reportsystem.model.responses.ApiResponse;
//import com.example.reportsystem.model.responses.LecturerResponse;
//import com.example.reportsystem.model.toDto.LecturerDto;
//import com.example.reportsystem.model.toDto.ShiftDto;
//import com.example.reportsystem.repository.LecturerRepository;
//import com.example.reportsystem.repository.LecturerShiftRepository;
//import com.example.reportsystem.repository.ShiftRepository;
//import com.example.reportsystem.service.LecturerService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class LecturerServiceImp implements LecturerService {
//    private final LecturerRepository lecturerRepository;
//    private final LecturerShiftRepository lecturerShiftRepository;
//    private final ShiftRepository shiftRepository;
//
//    public LecturerServiceImp(LecturerRepository lecturerRepository, LecturerShiftRepository lecturerShiftRepository, ShiftRepository shiftRepository) {
//        this.lecturerRepository = lecturerRepository;
//        this.lecturerShiftRepository = lecturerShiftRepository;
//        this.shiftRepository = shiftRepository;
//    }
//
//    @Override
//    public ResponseEntity<?> findAllTeacher() {
//        List<Lecturer> lecturerList =  lecturerRepository.findAll();
//        List<LecturerResponse> lecturerResponses = new ArrayList<>();
//        for(Lecturer lecturer : lecturerList){
//            List<LecturerShift> lecturerShifts = lecturerShiftRepository.findAllByLecturer(lecturer);
//            System.out.println(lecturerShifts);
//            List<ShiftDto> shiftName = new ArrayList<>();
//            for (LecturerShift lecturerShift: lecturerShifts){
//                Optional<Shift> shift = shiftRepository.findById(lecturerShift.getId());
//                if(shift.isPresent()){
//                    ShiftDto shiftDto = new ShiftDto();
//                    shiftDto.setId(shift.get().getId());
//                    shiftDto.setName(shift.get().getName());
//                    shiftDto.setDescription(shift.get().getDescription());
//                    shiftName.add(shiftDto);
//                }
//            }
//            LecturerResponse lecturerResponse = LecturerResponse.builder()
//                    .id(lecturer.getId())
//                    .name(lecturer.getName())
//                    .description(lecturer.getDescription())
//                    .shiftDtos(shiftName)
//                    .date(lecturer.getDate())
//                    .build();
//            lecturerResponses.add(lecturerResponse);
//
//        }
//
//        try {
//            return ResponseEntity.ok(ApiResponse.<List<LecturerResponse>>builder()
//                    .message("lecturer fetch success")
//                    .status(HttpStatus.OK)
//                    .payload(lecturerResponses)
//                    .build());
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(ApiResponse.builder()
//                            .message("Error fetching shifts")
//                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                            .build());
//        }
//
//    }
//
//    @Override
//    public ResponseEntity<?> save(UserRequest teacherRequest) {
//        Lecturer lecturerObj = null;
//        lecturerObj = Lecturer.builder()
//                .name(teacherRequest.getName())
//                .description(teacherRequest.getDescription())
//                .date(teacherRequest.getDate())
//                .build();
//        try {
//            Lecturer lecturer = lecturerRepository.save(lecturerObj);
//            return ResponseEntity.ok(ApiResponse.builder()
//                            .message("Add lecturer success")
//                            .status(HttpStatus.OK)
//                            .payload(lecturer)
//                            .build());
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
//                            .message("Add lecturer Error")
//                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .build());
//        }
//
//    }
//
//}
