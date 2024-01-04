package com.example.reportsystem.service.serviceImp;

import com.example.reportsystem.model.Shift;
import com.example.reportsystem.model.request.ShiftRequest;
import com.example.reportsystem.model.responses.ApiResponse;
import com.example.reportsystem.model.responses.ShiftResponse;
import com.example.reportsystem.repository.ShiftRepository;
import com.example.reportsystem.service.ShiftService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ShiftServiceImp implements ShiftService {
    private final ShiftRepository shiftRepository;

    public ShiftServiceImp(ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    @Override
    public ResponseEntity<?> findAll() {
        List<Shift> shifts = shiftRepository.findAll();
        try {
            return ResponseEntity.ok(ApiResponse.<List<Shift>>builder()
                    .message("shift fetch success")
                    .status(HttpStatus.OK)
                    .payload(shifts)
                    .build()
            );
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.builder()
                            .message("Error fetching shifts")
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .build());
        }

    }

    @Override
    public ResponseEntity<?> save(ShiftRequest shiftRequest) {
        Shift shift = null;
        shift = Shift.builder()
                .name(shiftRequest.getName())
                .description(shiftRequest.getDescription())
                .build();
        try {
            Shift shift1 = shiftRepository.save(shift);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("add shift success")
                            .status(HttpStatus.OK)
                    .payload(shift1)
                    .build());

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                            .message("add shift error")
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build());
        }

    }
}
