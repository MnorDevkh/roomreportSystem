package com.example.reportsystem.service.serviceImp;

import com.example.reportsystem.model.*;
import com.example.reportsystem.model.report.Room;
import com.example.reportsystem.model.request.UserRequest;
import com.example.reportsystem.model.responses.UserResponse;
import com.example.reportsystem.model.toDto.ShiftDto;
import com.example.reportsystem.model.toDto.SubjectDto;
import com.example.reportsystem.repository.*;
import com.example.reportsystem.repository.report.RoomRepository;
import com.example.reportsystem.service.UserService;
import com.example.reportsystem.utilities.response.EmptyObject;
import com.example.reportsystem.utilities.response.Message;
import com.example.reportsystem.utilities.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final ShiftRepository shiftRepository;
    private final RoomRepository roomRepository;
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
            List<UserResponse> userResponses = new ArrayList<>();
            List<User> userList  = userRepository.findAll();
            for(User user: userList){
                Optional<User> userOptional = userRepository.findById(user.getId());
                if (userOptional.isPresent()){
                    UserResponse userResponse = userResponseFlag(userOptional.get());
                    userResponses.add(userResponse);
                }
            }
                res.setData(userResponses);
                res.setStatus(true);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not\\");
        }
        return ResponseEntity.ok(res);
    }

    @Override
    public ResponseEntity<?> findUserById(long id) {
//        try {
//            Optional<User> userOptional = userRepository.findById(id);
//            List<UserResponse> userResponses = new ArrayList<>();
//            if (userOptional.isPresent()){
//                List<UserSubject> userSubjectList = userSubjectRepository.findAllByUserId(userOptional.get().getId());
//                List<SubjectDto> subjectDtos = new ArrayList<>();
//                for (UserSubject userSubject: userSubjectList){
//                    Optional<Subject> subject = subjectRepository.findById(userSubject.getSubject().getId());
//                    System.out.println("subject " + subject);
//                    if(subject.isPresent() && !subject.get().isStatus()){
//                        SubjectDto subjectDto = SubjectDto.builder()
//                                .id(subject.get().getId())
//                                .name(subject.get().getName())
//                                .description(subject.get().getDescription())
//                                .build();
//                        subjectDtos.add(subjectDto);
//                    }
//                }
//                List<UserShift> userShifts = userShiftRepository.findAllByUserId(userOptional.get().getId());
//                List<ShiftDto> shiftDtos = new ArrayList<>();
//                for(UserShift userShift : userShifts){
//                    Optional<Shift> shift = shiftRepository.findById(userShift.getShift().getId());
//                    if(shift.isPresent() && !shift.get().isStatus()){
//                        ShiftDto shiftDto = ShiftDto.builder()
//                                .id(shift.get().getId())
//                                .name(shift.get().getName())
//                                .description(shift.get().getDescription())
//                                .build();
//                        shiftDtos.add(shiftDto);
//                    }
//                }
//
//                UserResponse userResponse = UserResponse.builder()
//                        .id(userOptional.get().getId())
//                        .email(userOptional.get().getEmail())
//                        .firstName(userOptional.get().getFirstName())
//                        .lastName(userOptional.get().getLastName())
//                        .role(userOptional.get().getRole())
//                        .subject(subjectDtos)
//                        .shift(shiftDtos)
//                        .build();
//                userResponses.add(userResponse);
//            }
//
//            res.setStatus(true);
//            res.setMessage(message.getSuccess("User"));
//            res.setData(userResponses);
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not\\");
//        }
//        return ResponseEntity.ok(res);
        return null;
    }

    @Override
    public ResponseEntity<?> AddSubjectRoomShiftToLecturer(long id, UserRequest userRequest) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Lecturer not found"));

            Set<Subject> subjects = getSubjectsByIds(userRequest.getSubjectsId());
            Set<Shift> shifts = getShiftsByIds(userRequest.getShiftId());

            user.setSubjects(subjects);
            user.setShifts(shifts);

            userRepository.save(user);
            UserResponse userResponse = userResponseFlag(user);

            return ResponseEntity.ok(userResponse);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding subjects, shifts, and rooms to the lecturer.");
        }
    }

    @Override
    public ResponseEntity<?> findUserBySubject(long id) {
//        try {
//            List<UserSubject> userSubjectList = userSubjectRepository.findAllBySubjectId(id);
//            List<UserResponse> userResponses = new ArrayList<>();
//            for (UserSubject userSubject : userSubjectList) {
//                Optional<User> user = userRepository.findById(userSubject.getUser().getId());
//                if (user.isPresent()) {
//                    UserResponse userResponse = UserResponse.builder()
//                            .id(userSubject.getUser().getId())
//                            .firstName(userSubject.getUser().getFirstName())
//                            .lastName(userSubject.getUser().getLastName())
//                            .email(userSubject.getUser().getEmail())
//                            .role(userSubject.getUser().getRole())
//                            .build();
//                    userResponses.add(userResponse);
//
//                }
//                ApiResponse apiResponse = ApiResponse.builder()
//                        .payload(userResponses)
//                        .totalElement(Long.valueOf(userResponses.size()))
//                        .build();
//                res.setStatus(true);
//                res.setMessage(message.getSuccess("subject"));
//                res.setData(apiResponse);
//
//            }
//            return ResponseEntity.ok(res);
//        } catch (Exception e) {
//            emptyObject.setStatus(false);
//            emptyObject.setMessage(message.getFail("user"));
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(emptyObject);
//        }
        return null;
    }

    @Override
    public ResponseEntity<?> findUserByShift(long id) {
//        try {
//            List<UserShift> userSubjectList = userShiftRepository.findAllByShiftId(id);
//            List<UserResponse> userResponses = new ArrayList<>();
//            for (UserShift userShift : userSubjectList) {
//                Optional<User> user = userRepository.findById(userShift.getUser().getId());
//                if (user.isPresent()) {
//                    UserResponse userResponse = UserResponse.builder()
//                            .id(userShift.getUser().getId())
//                            .firstName(userShift.getUser().getFirstName())
//                            .lastName(userShift.getUser().getLastName())
//                            .email(userShift.getUser().getEmail())
//                            .role(userShift.getUser().getRole())
//                            .build();
//                    userResponses.add(userResponse);
//
//                }
//                ApiResponse apiResponse = ApiResponse.builder()
//                        .payload(userResponses)
//                        .totalElement(Long.valueOf(userResponses.size()))
//                        .build();
//                res.setStatus(true);
//                res.setMessage(message.getSuccess("subject"));
//                res.setData(apiResponse);
//
//            }
//            return ResponseEntity.ok(res);
//        } catch (Exception e) {
//            emptyObject.setStatus(false);
//            emptyObject.setMessage(message.getFail("user"));
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(emptyObject);
//        }
        return null;
    }

    @Override
    public ResponseEntity<?> findAllUser() {
        try {
            List<User> userList = userRepository.findAll();
            List<UserResponse> userResponses = new ArrayList<>();
            for (User user : userList) {
                UserResponse userResponse = userResponseFlag(user);
                System.out.println(userResponse);
                userResponses.add(userResponse);
            }
            return ResponseEntity.ok(userResponses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while processing users.");
        }
    }
    private UserResponse userResponseFlag(User user) {
        List<SubjectDto> subjectDtoList = new ArrayList<>();
        List<ShiftDto> shiftDtoList = new ArrayList<>();

        for (Subject subject : user.getSubjects()) {
            SubjectDto subjectDto = subjectFlag(subject.getId());
            System.out.println("subjectDto" +subjectDto);
            if (subjectDto != null) {
                System.out.println("subjectDto" + subjectDto);
                subjectDtoList.add(subjectDto);
            }
        }

        for (Shift shift : user.getShifts()) {
            ShiftDto shiftDto = shiftFlag(shift.getId());
            if (shiftDto != null) {
                shiftDtoList.add(shiftDto);
            }
        }

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .subject(subjectDtoList)
                .shift(shiftDtoList)
                .build();
    }
    private ShiftDto shiftFlag(Integer shiftId) {
        Optional<Shift> shift = shiftRepository.findById(shiftId);
        return shift.map(value -> ShiftDto.builder()
                .id(value.getId())
                .name(value.getName())
                .description(value.getDescription())
                .build()).orElse(null);
    }

    private SubjectDto subjectFlag(Integer id) {
        Optional<Subject> subject = subjectRepository.findById(id);
        System.out.println(subject);
        return subject.map(value -> SubjectDto.builder()
                .id(value.getId())
                .name(value.getName())
                .description(value.getDescription())
                .build()).orElse(null);
    }
    private Set<Subject> getSubjectsByIds(List<Integer> subjectIds) {
        return subjectRepository.findAllById(subjectIds).stream()
                .collect(Collectors.toSet());
    }

    private Set<Shift> getShiftsByIds(List<Integer> shiftIds) {
        return shiftRepository.findAllById(shiftIds).stream()
                .collect(Collectors.toSet());
    }

    private Set<Room> getRoomsByIds(List<Integer> roomIds) {
        return roomRepository.findAllById(roomIds).stream()
                .collect(Collectors.toSet());
    }
}
