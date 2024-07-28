package com.example.reportsystem.service.serviceImp;

import com.example.reportsystem.model.*;
import com.example.reportsystem.model.report.Report;
import com.example.reportsystem.model.request.ShiftRequest;
import com.example.reportsystem.model.responses.ApiResponse;
import com.example.reportsystem.model.responses.ShiftResponse;
import com.example.reportsystem.model.responses.SubjectResponse;
import com.example.reportsystem.repository.ShiftRepository;
import com.example.reportsystem.service.ShiftService;
import com.example.reportsystem.utilities.response.EmptyObject;
import com.example.reportsystem.utilities.response.Message;
import com.example.reportsystem.utilities.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
public class ShiftServiceImp implements ShiftService {
    private final ShiftRepository shiftRepository;
    ResponseObject res = new ResponseObject();
    Message message = new Message();
    EmptyObject emptyObject = new EmptyObject();

    @Override
    public ResponseEntity<?> findAll() {
        try {
            List<Shift> shifts = shiftRepository.findAll();
            List<ShiftResponse> shiftResponse = shifts.stream()
                        .filter(shift -> !shift.isDeleted())
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
    public ResponseEntity<?> findShiftCurrenUser(Integer pageNumber, Integer pageSize, String sortBy, boolean ascending) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = (User) authentication.getPrincipal();
            List<ShiftResponse> shiftResponses = new ArrayList<>();
            Sort.Direction sortDirection = ascending ? Sort.Direction.ASC : Sort.Direction.DESC;
            PageRequest pageable = PageRequest.of(pageNumber - 1, pageSize, sortDirection, sortBy);
            Page<Shift> pageResult = shiftRepository.findByDeletedFalseAndUsers(pageable, currentUser);
            for (Shift shift : pageResult) {
                Optional<Shift> shiftOptional = shiftRepository.findById(shift.getId());
                if (shiftOptional.isPresent() && !shiftOptional.get().isDeleted()) {
                    ShiftResponse shiftResponse = ShiftResponse.builder()
                            .id(shiftOptional.get().getId())
                            .name(shiftOptional.get().getName())
                            .description(shiftOptional.get().getDescription())
                            .date(shiftOptional.get().getDate())
                            .build();
                    shiftResponses.add(shiftResponse);
                }
            }
            ApiResponse res = new ApiResponse(true, "Fetch books successful!", shiftResponses, pageResult.getNumber() + 1, pageResult.getSize(), pageResult.getTotalPages(), pageResult.getTotalElements());
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            emptyObject.setStatus(false);
            emptyObject.setMessage(message.loginFail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(emptyObject);
        }
    }

    @Override
    public ResponseEntity<?> findShiftByUserId(long userId) {
        try {
            List<Shift> subjectList = shiftRepository.findByUserId(userId);
            List<ShiftResponse> shiftResponses = new ArrayList<>();
            for (Shift shift : subjectList) {
                Optional<Shift> shiftOptional = shiftRepository.findById(shift.getId());
                if (shiftOptional.isPresent() && !shiftOptional.get().isDeleted()) {
                    ShiftResponse shiftResponse = ShiftResponse.builder()
                            .id(shiftOptional.get().getId())
                            .name(shiftOptional.get().getName())
                            .description(shiftOptional.get().getDescription())
                            .date(shiftOptional.get().getDate())
                            .build();
                    shiftResponses.add(shiftResponse);
                }
            }
            ResponseObject res = new ResponseObject();
            res.setStatus(true);
            res.setMessage("fetch data successfully");
            res.setData(shiftResponses);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            emptyObject.setStatus(false);
            emptyObject.setMessage(message.loginFail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(emptyObject);
        }
    }

    @Override
    public ResponseEntity<?> findById(Integer id) {
        Optional<Shift> shiftOptional = shiftRepository.findById(id);
        return ResponseEntity.ok(shiftOptional);
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
    public ResponseEntity<?> deleteById(Integer id) {
        Optional<Shift> shift = shiftRepository.findById(id);
            if (shift.isPresent() && !shift.get().isDeleted()){
               Shift shift1 = Shift.builder()
                       .id(shift.get().getId())
                       .name(shift.get().getName())
                       .description(shift.get().getDescription())
                       .date(shift.get().getDate())
                       .deleteAtDate(LocalDate.now())
                       .deleted(true)
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
    public ResponseEntity<?> updateById(Integer id,ShiftRequest shiftRequest) {
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
