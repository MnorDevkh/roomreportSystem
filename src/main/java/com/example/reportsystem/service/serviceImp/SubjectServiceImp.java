package com.example.reportsystem.service.serviceImp;

import com.example.reportsystem.model.*;
import com.example.reportsystem.model.request.SubjectRequest;
import com.example.reportsystem.model.responses.ApiResponse;
import com.example.reportsystem.model.responses.SubjectResponse;
import com.example.reportsystem.repository.SubjectRepository;
import com.example.reportsystem.service.SubjectService;
import com.example.reportsystem.utilities.response.EmptyObject;
import com.example.reportsystem.utilities.response.Message;
import com.example.reportsystem.utilities.response.ResponseObject;
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
    ResponseObject res = new ResponseObject();
    Message message = new Message();
    EmptyObject emptyObject = new EmptyObject();

    @Override
    public ResponseEntity<?> findAllSubject() {
        List<Subject> subjects = subjectRepository.findAll();
        List<SubjectResponse> subjectResponses = subjects.stream()
                .filter(subject -> !subject.isDeleted())
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

        } catch (Exception e) {
            emptyObject.setStatus(false);
            emptyObject.setMessage(message.loginFail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(emptyObject);
        }
        return ResponseEntity.ok(res);
    }

    @Override
    public ResponseEntity<?> findSubject() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = (User) authentication.getPrincipal();
            List<Subject> subjectList = subjectRepository.findByDeletedFalseAndUsers(currentUser);
            List<SubjectResponse> subjectResponses = new ArrayList<>();
            for (Subject subject : subjectList) {
                Optional<Subject> subjectOptional = subjectRepository.findById(subject.getId());
                if (subjectOptional.isPresent()) {
                    SubjectResponse subjectResponse = SubjectResponse.builder()
                            .id(subjectOptional.get().getId())
                            .name(subjectOptional.get().getName())
                            .description(subjectOptional.get().getDescription())
                            .date(LocalDate.now())
                            .build();
                    subjectResponses.add(subjectResponse);
                }
            }

            ResponseObject res = new ResponseObject();
            res.setStatus(true);
            res.setMessage("fetch data successfully");
            res.setData(subjectResponses);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            emptyObject.setStatus(false);
            emptyObject.setMessage(message.loginFail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(emptyObject);
        }
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
            Subject subject1 = subjectRepository.save(subject);
            SubjectResponse subjectResponse = new SubjectResponse();
            subjectResponse.setId(subject1.getId());
            subjectResponse.setName(subject1.getName());
            subjectResponse.setDescription(subject1.getDescription());
            subjectResponse.setDate(subject1.getDate());
            res.setStatus(true);
            res.setMessage(message.getSuccess("Subject"));
            res.setData(subjectResponse);

        } catch (Exception e) {
            emptyObject.setStatus(false);
            emptyObject.setMessage(message.loginFail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(emptyObject);
        }
        return ResponseEntity.ok(res);
    }


    @Override
    public ResponseEntity<?> findSubjectByUser(long id) {
        try {
            List<Subject> subjectList = subjectRepository.findByUserId(id);
            List<SubjectResponse> subjectResponses = new ArrayList<>();
            for (Subject subject : subjectList) {
                Optional<Subject> subjectOptional = subjectRepository.findById(subject.getId());
                if (subjectOptional.isPresent()) {
                    SubjectResponse subjectResponse = SubjectResponse.builder()
                            .id(subjectOptional.get().getId())
                            .name(subjectOptional.get().getName())
                            .description(subjectOptional.get().getDescription())
                            .date(LocalDate.now())
                            .build();
                    subjectResponses.add(subjectResponse);
                }
            }

            ResponseObject res = new ResponseObject();
            res.setStatus(true);
            res.setMessage("fetch data successfully");
            res.setData(subjectResponses);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            emptyObject.setStatus(false);
            emptyObject.setMessage(message.loginFail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(emptyObject);

        }
    }

    @Override
    public ResponseEntity<?> deleteById(Integer id) {
        Optional<Subject> subject = null;
        subject = subjectRepository.findById(id);
        try {
            if (!subject.get().isDeleted()) {
                if (subject.isPresent()) {
                    Subject subject1 = Subject.builder()
                            .id(subject.get().getId())
                            .name(subject.get().getName())
                            .description(subject.get().getDescription())
                            .date(subject.get().getDate())
                            .deleteAtDate(subject.get().getDeleteAtDate())
                            .deleted(true)
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
    public ResponseEntity<?> updateById(Integer id, SubjectRequest subjectRequest) {
        try {
            Optional<Subject> subjectOptional = subjectRepository.findById(id);
            if (subjectOptional.isPresent()) {
                Subject subject = Subject.builder()
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
        } catch (Exception e) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
        return ResponseEntity.ok(res);
    }

    @Override
    public ResponseEntity<?> findById(Integer id) {
        Optional<Subject> subjectOptional = subjectRepository.findById(id);
        return ResponseEntity.ok(subjectOptional);
    }


}
