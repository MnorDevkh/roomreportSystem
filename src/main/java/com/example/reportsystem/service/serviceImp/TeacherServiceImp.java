package com.example.reportsystem.service.serviceImp;

import com.example.reportsystem.model.Teacher;
import com.example.reportsystem.model.request.TeacherRequest;
import com.example.reportsystem.model.responses.ApiResponse;
import com.example.reportsystem.repository.TeacherRepository;
import com.example.reportsystem.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImp implements TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherServiceImp(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public ResponseEntity<?> findAllTeacher() {
        List<Teacher> teacherList  =  teacherRepository.findAll();
        try {
            return ResponseEntity.ok(ApiResponse.<List<Teacher>>builder()
                    .message("teacher fetch success")
                    .status(HttpStatus.OK)
                    .payload(teacherList)
                    .build());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.builder()
                            .message("Error fetching shifts")
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .build());
        }

    }

    @Override
    public ResponseEntity<?> save(TeacherRequest teacherRequest) {
        Teacher teacherObj = null;
        teacherObj = Teacher.builder()
                .name(teacherRequest.getName())
                .description(teacherRequest.getDescription())
                .date(teacherRequest.getDate())
                .build();
        try {
            Teacher teacher = teacherRepository.save(teacherObj);
            return ResponseEntity.ok(ApiResponse.builder()
                            .message("Add teacher success")
                            .status(HttpStatus.OK)
                            .payload(teacher)
                            .build());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                            .message("Add teacher Error")
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build());
        }

    }

}
