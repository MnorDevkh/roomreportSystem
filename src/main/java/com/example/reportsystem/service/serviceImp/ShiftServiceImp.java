package com.example.reportsystem.service.serviceImp;

import com.example.reportsystem.model.*;
import com.example.reportsystem.model.request.ShiftRequest;
import com.example.reportsystem.model.responses.ApiResponse;
import com.example.reportsystem.model.responses.ShiftResponse;
import com.example.reportsystem.model.responses.SubjectResponse;
import com.example.reportsystem.repository.ShiftRepository;
import com.example.reportsystem.repository.UserShiftRepository;
import com.example.reportsystem.service.ShiftService;
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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShiftServiceImp implements ShiftService {
    private final ShiftRepository shiftRepository;
    private final UserShiftRepository userShiftRepository;
    ResponseObject res = new ResponseObject();
    Message message = new Message();
    EmptyObject emptyObject = new EmptyObject();


    @Override
    public ResponseEntity<?> findAll() {

        try {
            List<Shift> shifts = shiftRepository.findAll();
            List<ShiftResponse> shiftResponse = shifts.stream()
                        .filter(shift -> !shift.isStatus())
                        .map(shift -> ShiftResponse.builder()
                                .id(shift.getId())
                                .name(shift.getName())
                                .description(shift.getDescription())
                                .build())
                    .collect(Collectors.toList());
            res.setStatus(true);
            res.setMessage(message.getSuccess("subject"));
            res.setData(shiftResponse);

        }catch (Exception e){
            emptyObject.setStatus(false);
            emptyObject.setMessage(message.getFail("Status"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(emptyObject);
        }
        return ResponseEntity.ok(res);

    }

    @Override
    public ResponseEntity<?> findShiftCurrenUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = (User) authentication.getPrincipal();
            List<UserShift> userShiftList = userShiftRepository.findAllByUser(currentUser);
            List<SubjectResponse> subjectResponses = new ArrayList<>();

            for (UserShift userShift : userShiftList) {
                Optional<Shift> shiftOptional = shiftRepository.findById(userShift.getShift().getId());
                if (shiftOptional.isPresent() && !shiftOptional.get().isStatus()) {
                    SubjectResponse subjectResponse = SubjectResponse.builder()
                            .id(shiftOptional.get().getId())
                            .name(shiftOptional.get().getName())
                            .description(shiftOptional.get().getDescription())
                            .date(shiftOptional.get().getDate())
                            .build();
                    subjectResponses.add(subjectResponse);
                }
            }

            ApiResponse apiResponse = ApiResponse.builder()
                    .totalElement((long) subjectResponses.size())
                    .payload(subjectResponses)
                    .build();
            res.setStatus(true);
            res.setMessage(message.getSuccess("shift"));
            res.setData(apiResponse);

            return ResponseEntity.ok(res);
        } catch (Exception e) {
            emptyObject.setStatus(false);
            emptyObject.setMessage(message.loginFail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(emptyObject);
        }
    }


    @Override
    public ResponseEntity<?> save(ShiftRequest shiftRequest) {
        System.out.println(shiftRequest);
        Shift shift = null;
        shift = Shift.builder()
                .name(shiftRequest.getName())
                .description(shiftRequest.getDescription())
                .date(LocalDate.now())
                .build();
        try {
            Shift shift1 = shiftRepository.save(shift);
            res.setStatus(true);
            res.setMessage("add shift success");
            res.setData(shift1);

        }catch (Exception e){
            emptyObject.setStatus(false);
            emptyObject.setMessage(message.invalidRequest("shift"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(emptyObject);
        }
        return ResponseEntity.ok(res);
    }

    @Override
    public ResponseEntity<?> deleteById(long id) {
        Optional<Shift> shift = shiftRepository.findById(id);
            if (shift.isPresent() && !shift.get().isStatus()){
               Shift shift1 = Shift.builder()
                       .id(shift.get().getId())
                       .name(shift.get().getName())
                       .description(shift.get().getDescription())
                       .date(shift.get().getDate())
                       .deleteAtDate(LocalDate.now())
                       .status(true)
                       .build();
               shiftRepository.save(shift1);
            }
            try {
                res.setStatus(true);
                res.setMessage("Shift id " + shift.get().getId() + " delete success");
                return ResponseEntity.ok(res);
            }
            catch (Exception e){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shift id " + shift.get().getId() + " false");
            }

    }

    @Override
    public ResponseEntity<?> updateById(long id,ShiftRequest shiftRequest) {
        try {
            Optional<Shift> shiftOptional = shiftRepository.findById(id);
            if (shiftOptional.isPresent()){
                Shift shift = Shift.builder()
                        .id(shiftOptional.get().getId())
                        .name(shiftRequest.getName())
                        .description(shiftRequest.getDescription())
                        .date(shiftOptional.get().getDate())
                        .build();
                shiftRepository.save(shift);
                res.setStatus(true);
                res.setMessage("update shift success");
                res.setData(shift);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
        return ResponseEntity.ok(res);
    }
}
