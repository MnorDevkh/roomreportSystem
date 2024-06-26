package com.example.reportsystem.service;

import com.example.reportsystem.model.User;
import com.example.reportsystem.model.request.LectureRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    UserDetailsService userDetailsService();
    ResponseEntity<?> findAllUser();
    ResponseEntity<?> findUserBySubject(long id);
    ResponseEntity<?> findUserByShift(long id);
    ResponseEntity<?> findUser();
    ResponseEntity<?> findUserById(long id);
    ResponseEntity<?> AddSubjectRoomShiftToLecturer(LectureRequest lectureRequest);

}
