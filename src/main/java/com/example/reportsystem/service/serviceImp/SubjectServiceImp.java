package com.example.reportsystem.service.serviceImp;

import com.example.reportsystem.model.*;
import com.example.reportsystem.model.request.ShiftRequest;
import com.example.reportsystem.model.request.SubjectRequest;
import com.example.reportsystem.model.responses.ApiResponse;
import com.example.reportsystem.model.responses.SubjectResponse;
import com.example.reportsystem.model.responses.UserSubjectResponse;
import com.example.reportsystem.model.toDto.SubjectDto;
import com.example.reportsystem.model.toDto.UserDto;
import com.example.reportsystem.repository.SubjectRepository;
import com.example.reportsystem.repository.UserRepository;
import com.example.reportsystem.repository.UserSubjectRepository;
import com.example.reportsystem.service.SubjectService;
import com.example.reportsystem.utilities.response.EmptyObject;
import com.example.reportsystem.utilities.response.Message;
import com.example.reportsystem.utilities.response.ResponseObject;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectServiceImp implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final UserSubjectRepository userSubjectRepository;
    ResponseObject res = new ResponseObject();
    Message message = new Message();
    EmptyObject emptyObject = new EmptyObject();

    @Override
    public ResponseEntity<?> findAllSubject() {
        List<Subject> subjects = subjectRepository.findAll();
        List<SubjectResponse> subjectResponses = subjects.stream()
                .filter(subject -> !subject.isStatus())
                .map(subject -> SubjectResponse.builder()
                        .id(subject.getId())
                        .name(subject.getName())
                        .description(subject.getDescription())
                        .date(subject.getDate())
                        .build())
                .collect(Collectors.toList());

        try {
            res.setStatus(true);
            res.setMessage(message.getSuccess("Report"));
            res.setData(subjectResponses);

        }
        catch (Exception e){
            emptyObject.setStatus(false);
            emptyObject.setMessage(message.loginFail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(emptyObject);
        }
        return ResponseEntity.ok(res);
    }

    @Override
    public ResponseEntity<?> findSubject() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        List<UserSubject> userSubjectList = userSubjectRepository.findAllByUser(currentUser);
        List<SubjectResponse> subjectResponses = new ArrayList<>();
        for (UserSubject userSubject: userSubjectList){
            Optional<Subject> subject = subjectRepository.findById(userSubject.getSubject().getId());
            if(subject.isPresent() && !subject.get().isStatus()){
                SubjectResponse subjectResponse =  SubjectResponse.builder()
                        .id(subject.get().getId())
                        .name(subject.get().getName())
                        .description(subject.get().getDescription())
                        .date(LocalDate.now())
                        .build();
                subjectResponses.add(subjectResponse);
            }
        }
        try {
            ApiResponse apiResponse = ApiResponse.builder()
                    .totalElement((long) subjectResponses.size())
                    .payload(subjectResponses)
                    .build();
            res.setStatus(true);
            res.setMessage(message.getSuccess("Report"));
            res.setData(apiResponse);
        }
        catch (Exception e){
            emptyObject.setStatus(false);
            emptyObject.setMessage(message.loginFail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(emptyObject);
        }
        return ResponseEntity.ok(res);
    }

    @Override
    public ResponseEntity<?> addSubject(SubjectRequest subjectRequest) {
        Subject subject = null;
        subject = Subject.builder()
                .name(subjectRequest.getName())
                .description(subjectRequest.getDescription())
                .date(LocalDate.now())
                .build();

        try {
            Subject subject1= subjectRepository.save(subject);
            SubjectResponse subjectResponse = new SubjectResponse();
            subjectResponse.setId(subject1.getId());
            subjectResponse.setName(subject1.getName());
            subjectResponse.setDescription(subject1.getDescription());
            subjectResponse.setDate(subject1.getDate());
            res.setStatus(true);
            res.setMessage(message.getSuccess("report"));
            res.setData(subjectResponse);

        }catch (Exception e){
                    emptyObject.setStatus(false);
                    emptyObject.setMessage(message.loginFail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(emptyObject);
        }
        return ResponseEntity.ok(res);
    }


    @Override
    public ResponseEntity<?> findSubjectByUser(long id) {
        try {
            List<UserSubject> userSubjectList = userSubjectRepository.findAllByUserId(id);
            List<SubjectResponse> subjectResponses = new ArrayList<>();
                for(UserSubject userSubject: userSubjectList){
                    Optional<Subject> subject = subjectRepository.findById(userSubject.getSubject().getId());
                        if(!subject.get().isStatus()){
                            if(subject.isPresent()){
                                SubjectResponse subjectResponse = SubjectResponse.builder()
                                    .id(userSubject.getSubject().getId())
                                    .name(userSubject.getSubject().getName())
                                    .description(userSubject.getSubject().getDescription())
                                    .date(userSubject.getDate())
                                .build();
                subjectResponses.add(subjectResponse);
            }
        }

        ApiResponse apiResponse = ApiResponse.builder()
                .payload(subjectResponses)
                .totalElement(Long.valueOf(subjectResponses.size()))
                .build();
        res.setStatus(true);
        res.setMessage(message.getSuccess("subject"));
        res.setData(apiResponse);

    }
    return ResponseEntity.ok(res);
    }catch (Exception e){
    return null;
}

    }

    @Override
    public ResponseEntity<?> deleteById(long id) {
        Optional<Subject> subject = null;
        subject = subjectRepository.findById(id);
        try {
            if(!subject.get().isStatus()) {
                if (subject.isPresent()) {
                    Subject subject1 = Subject.builder()
                            .id(subject.get().getId())
                            .name(subject.get().getName())
                            .description(subject.get().getDescription())
                            .date(subject.get().getDate())
                            .deleteAtDate(subject.get().getDeleteAtDate())
                            .status(true)
                            .build();
                    subjectRepository.save(subject1);
                }
            }
            res.setStatus(true);
            res.setMessage("Subject " + subject.get().getId() + " delete success!");
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subject " + subject.get().getId() + " not fond!");
        }
    }

    @Override
    public ResponseEntity<?> updateById(long id, SubjectRequest subjectRequest) {
        try {
            Optional<Subject> subjectOptional = subjectRepository.findById(id);
            if (subjectOptional.isPresent()){
                Subject subject=Subject.builder()
                        .id(subjectOptional.get().getId())
                        .name(subjectRequest.getName())
                        .description(subjectRequest.getDescription())
                        .date(subjectOptional.get().getDate())
                        .build();
                subjectRepository.save(subject);
                res.setMessage("update success");
                res.setStatus(true);
                res.setData(subject);
            }
        }catch (Exception e){
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
        return ResponseEntity.ok(res);
    }


}
