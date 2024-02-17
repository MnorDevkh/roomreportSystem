package com.example.reportsystem.service.serviceImp;

import com.example.reportsystem.model.Subject;
import com.example.reportsystem.model.User;
import com.example.reportsystem.model.UserShift;
import com.example.reportsystem.model.UserSubject;
import com.example.reportsystem.model.request.UserSubjectRequest;
import com.example.reportsystem.model.responses.UserSubjectResponse;
import com.example.reportsystem.repository.SubjectRepository;
import com.example.reportsystem.repository.SubjectToUserRepository;
import com.example.reportsystem.repository.UserRepository;
import com.example.reportsystem.service.UserSubjectService;
import com.example.reportsystem.utilities.response.EmptyObject;
import com.example.reportsystem.utilities.response.Message;
import com.example.reportsystem.utilities.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSubjectServiceImp implements UserSubjectService {
private final SubjectToUserRepository subjectToUserRepository;
private final SubjectRepository subjectRepository;
private final UserRepository userRepository;
ResponseObject res = new ResponseObject();
Message message = new Message();
EmptyObject emptyObject = new EmptyObject();

    @Override
    public ResponseEntity<?> save(UserSubjectRequest userSubjectRequest) {
        // Check if the record already exists
        if (subjectToUserRepository.existsByUserIdAndSubjectId(
                userSubjectRequest.getUser(),
                userSubjectRequest.getSubject())) {
            res.setMessage("User already associated with this subject");
            res.setStatus(false);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
        }
        UserSubject userSubject = UserSubject.builder()
                .user(User.builder().id(userSubjectRequest.getUser()).build())
                .subject(Subject.builder().id(userSubjectRequest.getSubject()).build())
                .date(LocalDate.now())
                .build();
        try {
            subjectToUserRepository.save(userSubject);
            Optional<Subject> subject = subjectRepository.findById(userSubjectRequest.getSubject());
            Optional<User> user = userRepository.findById(userSubjectRequest.getUser());
            UserSubjectResponse userSubjectResponse = UserSubjectResponse.builder()
                    .id(userSubject.getId())
                    .subject(subject.get().toDto())
                    .user(user.get().toDto())
                    .build();
            res.setMessage("Subject add to User success");
            res.setStatus(true);
            res.setData(userSubjectResponse);
            return ResponseEntity.ok(res);
        }catch (Exception e){
            emptyObject.setStatus(false);
            emptyObject.setMessage("Subject Fail");
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(emptyObject);
        }

    }

    @Override
    public ResponseEntity<?> deleteById(long id) {
        Optional<UserSubject> userShiftOptional = subjectToUserRepository.findById(id);
        if (userShiftOptional.isPresent()) {
            subjectToUserRepository.deleteById(id);
            res.setMessage("User shift deleted successfully");
            res.setStatus(true);
            return ResponseEntity.ok(res);
        } else {
            res.setMessage("User shift not found");
            res.setStatus(false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }
    }

    @Override
    public ResponseEntity<?> deleteByUserSubject(UserSubjectRequest userSubjectRequest) {
        if (subjectToUserRepository.existsByUserIdAndSubjectId(
                userSubjectRequest.getUser(),
                userSubjectRequest.getSubject())) {
            subjectToUserRepository.deleteByUserIdAndSubjectId(
                    userSubjectRequest.getUser(),
                    userSubjectRequest.getSubject());
            res.setMessage("User subject deleted successfully");
            res.setStatus(true);
            return ResponseEntity.ok(res);
        } else {
            res.setMessage("User subject not found");
            res.setStatus(false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }
    }
}
