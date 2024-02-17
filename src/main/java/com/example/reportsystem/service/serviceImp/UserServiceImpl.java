package com.example.reportsystem.service.serviceImp;

import com.example.reportsystem.model.*;
import com.example.reportsystem.model.responses.ApiResponse;
import com.example.reportsystem.model.responses.SubjectResponse;
import com.example.reportsystem.model.responses.UserResponse;
import com.example.reportsystem.model.toDto.ShiftDto;
import com.example.reportsystem.model.toDto.SubjectDto;
import com.example.reportsystem.repository.*;
import com.example.reportsystem.service.UserService;
import com.example.reportsystem.utilities.response.EmptyObject;
import com.example.reportsystem.utilities.response.Message;
import com.example.reportsystem.utilities.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserSubjectRepository userSubjectRepository;
    private final SubjectRepository subjectRepository;
    private final UserShiftRepository userShiftRepository;
    private final ShiftRepository shiftRepository;
    ResponseObject res = new ResponseObject();
    Message message = new Message();
    EmptyObject emptyObject = new EmptyObject();
    @Override
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


    @Override
    public ResponseEntity<?> findUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = (User) authentication.getPrincipal();
                List<UserSubject> userSubjectList = userSubjectRepository.findAllByUser(currentUser);
                List<SubjectDto> subjectDtos = new ArrayList<>();
                for (UserSubject userSubject: userSubjectList){
                    Optional<Subject> subject = subjectRepository.findById(userSubject.getSubject().getId());
                    System.out.println("subject " + subject);
                    if(subject.isPresent() && !subject.get().isStatus()){
                        SubjectDto subjectDto = new SubjectDto();
                        subjectDto.setId(subject.get().getId());
                        subjectDto.setName(subject.get().getName());
                        subjectDto.setDescription(subject.get().getDescription());
                        subjectDtos.add(subjectDto);
                    }
                }
                List<UserShift> userShifts = userShiftRepository.findAllByUser(currentUser);
                List<ShiftDto> shiftDtos = new ArrayList<>();
                for(UserShift userShift : userShifts){
                    Optional<Shift> shift = shiftRepository.findById(userShift.getShift().getId());
                    if(shift.isPresent() && !shift.get().isStatus()){
                        ShiftDto shiftDto = new ShiftDto();
                        shiftDto.setId(shift.get().getId());
                        shiftDto.setName(shift.get().getName());
                        shiftDto.setDescription(shift.get().getDescription());
                        shiftDtos.add(shiftDto);
                    }
                }
                UserResponse userResponse = UserResponse.builder()
                        .id(currentUser.getId())
                        .email(currentUser.getEmail())
                        .firstName(currentUser.getFirstName())
                        .lastName(currentUser.getLastName())
                        .role(currentUser.getRole())
                        .subject(subjectDtos)
                        .shift(shiftDtos)
                        .build();

            if (authentication != null) {
                res.setStatus(true);
                res.setMessage(message.getSuccess("User"));
                res.setData(userResponse);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not\\");
        }

        return ResponseEntity.ok(res);
    }

    @Override
    public ResponseEntity<?> findUserById(long id) {
        try {
            Optional<User> userOptional = userRepository.findById(id);
            List<UserResponse> userResponses = new ArrayList<>();
            if (userOptional.isPresent()){
                List<UserSubject> userSubjectList = userSubjectRepository.findAllByUserId(userOptional.get().getId());
                List<SubjectDto> subjectDtos = new ArrayList<>();
                for (UserSubject userSubject: userSubjectList){
                    Optional<Subject> subject = subjectRepository.findById(userSubject.getSubject().getId());
                    System.out.println("subject " + subject);
                    if(subject.isPresent() && !subject.get().isStatus()){
                        SubjectDto subjectDto = SubjectDto.builder()
                                .id(subject.get().getId())
                                .name(subject.get().getName())
                                .description(subject.get().getDescription())
                                .build();
                        subjectDtos.add(subjectDto);
                    }
                }
                List<UserShift> userShifts = userShiftRepository.findAllByUserId(userOptional.get().getId());
                List<ShiftDto> shiftDtos = new ArrayList<>();
                for(UserShift userShift : userShifts){
                    Optional<Shift> shift = shiftRepository.findById(userShift.getShift().getId());
                    if(shift.isPresent() && !shift.get().isStatus()){
                        ShiftDto shiftDto = ShiftDto.builder()
                                .id(shift.get().getId())
                                .name(shift.get().getName())
                                .description(shift.get().getDescription())
                                .build();
                        shiftDtos.add(shiftDto);
                    }
                }

                UserResponse userResponse = UserResponse.builder()
                        .id(userOptional.get().getId())
                        .email(userOptional.get().getEmail())
                        .firstName(userOptional.get().getFirstName())
                        .lastName(userOptional.get().getLastName())
                        .role(userOptional.get().getRole())
                        .subject(subjectDtos)
                        .shift(shiftDtos)
                        .build();
                userResponses.add(userResponse);
            }

            res.setStatus(true);
            res.setMessage(message.getSuccess("User"));
            res.setData(userResponses);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not\\");
        }
        return ResponseEntity.ok(res);

    }

    @Override
    public ResponseEntity<?> findUserBySubject(long id) {
        try {
            List<UserSubject> userSubjectList = userSubjectRepository.findAllBySubjectId(id);
            List<UserResponse> userResponses = new ArrayList<>();
            for (UserSubject userSubject : userSubjectList) {
                Optional<User> user = userRepository.findById(userSubject.getUser().getId());
                if (user.isPresent()) {
                    UserResponse userResponse = UserResponse.builder()
                            .id(userSubject.getUser().getId())
                            .firstName(userSubject.getUser().getFirstName())
                            .lastName(userSubject.getUser().getLastName())
                            .email(userSubject.getUser().getEmail())
                            .role(userSubject.getUser().getRole())
                            .build();
                    userResponses.add(userResponse);

                }
                ApiResponse apiResponse = ApiResponse.builder()
                        .payload(userResponses)
                        .totalElement(Long.valueOf(userResponses.size()))
                        .build();
                res.setStatus(true);
                res.setMessage(message.getSuccess("subject"));
                res.setData(apiResponse);

            }
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            emptyObject.setStatus(false);
            emptyObject.setMessage(message.getFail("user"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(emptyObject);
        }
    }

    @Override
    public ResponseEntity<?> findUserByShift(long id) {
        try {
            List<UserShift> userSubjectList = userShiftRepository.findAllByShiftId(id);
            List<UserResponse> userResponses = new ArrayList<>();
            for (UserShift userShift : userSubjectList) {
                Optional<User> user = userRepository.findById(userShift.getUser().getId());
                if (user.isPresent()) {
                    UserResponse userResponse = UserResponse.builder()
                            .id(userShift.getUser().getId())
                            .firstName(userShift.getUser().getFirstName())
                            .lastName(userShift.getUser().getLastName())
                            .email(userShift.getUser().getEmail())
                            .role(userShift.getUser().getRole())
                            .build();
                    userResponses.add(userResponse);

                }
                ApiResponse apiResponse = ApiResponse.builder()
                        .payload(userResponses)
                        .totalElement(Long.valueOf(userResponses.size()))
                        .build();
                res.setStatus(true);
                res.setMessage(message.getSuccess("subject"));
                res.setData(apiResponse);

            }
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            emptyObject.setStatus(false);
            emptyObject.setMessage(message.getFail("user"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(emptyObject);
        }
    }

    @Override
    public ResponseEntity<?> findAllUser() {
        try {
            List<User> userList = userRepository.findAll();
            List<UserResponse> userResponses = new ArrayList<>();
            for (User user: userList){
                List<UserSubject> userSubjectList = userSubjectRepository.findAllByUserId(user.getId());
                List<SubjectDto> subjectDtos = new ArrayList<>();
                for (UserSubject userSubject: userSubjectList){
                    Optional<Subject> subject = subjectRepository.findById(userSubject.getSubject().getId());
                    System.out.println("subject " + subject);
                    if(subject.isPresent() && !subject.get().isStatus()){
                        SubjectDto subjectDto = SubjectDto.builder()
                                .id(subject.get().getId())
                                .name(subject.get().getName())
                                .description(subject.get().getDescription())
                                .build();
                        subjectDtos.add(subjectDto);
                    }
                }
                List<UserShift> userShifts = userShiftRepository.findAllByUserId(user.getId());
                List<ShiftDto> shiftDtos = new ArrayList<>();
                for(UserShift userShift : userShifts){
                    Optional<Shift> shift = shiftRepository.findById(userShift.getShift().getId());
                    if(shift.isPresent() && !shift.get().isStatus()){
                        ShiftDto shiftDto = ShiftDto.builder()
                                .id(shift.get().getId())
                                .name(shift.get().getName())
                                .description(shift.get().getDescription())
                                .build();
                        shiftDtos.add(shiftDto);
                    }
                }

                UserResponse userResponse = UserResponse.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .role(user.getRole())
                        .subject(subjectDtos)
                        .shift(shiftDtos)
                        .build();
                userResponses.add(userResponse);
            }

                res.setStatus(true);
                res.setMessage(message.getSuccess("User"));
                res.setData(userResponses);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not\\");
        }
        return ResponseEntity.ok(res);
    }
}
