package com.example.reportsystem.service.serviceImp;

import com.example.reportsystem.model.Shift;
import com.example.reportsystem.model.User;
import com.example.reportsystem.model.request.UserShiftRequest;
import com.example.reportsystem.model.responses.UserShiftResponse;
import com.example.reportsystem.repository.ShiftRepository;
import com.example.reportsystem.repository.UserRepository;
import com.example.reportsystem.service.UserShiftService;
import com.example.reportsystem.utilities.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserShiftServiceImp implements UserShiftService {

//    private final UserShiftRepository userShiftRepository;
    private final ShiftRepository shiftRepository;
    private final UserRepository userRepository;
    private ResponseObject res= new ResponseObject();
    @Override
    public ResponseEntity<?> save(UserShiftRequest userShiftRequest) {
//        if (userShiftRepository.existsByUserIdAndShiftId(
//                userShiftRequest.getUser(),
//                userShiftRequest.getShift())) {
//            res.setMessage("User already assigned to this shift");
//            res.setStatus(false);
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
//        }
//        UserShift userShift = UserShift.builder()
//                .user(User.builder().id(userShiftRequest.getUser()).build())
//                .shift(Shift.builder().id(userShiftRequest.getShift()).build())
//                .build();
//        userShiftRepository.save(userShift);
//        Optional<User> user = userRepository.findById(userShift.getUser().getId());
//        Optional<Shift> shift = shiftRepository.findById(userShift.getShift().getId());
//        UserShiftResponse userShiftResponse = UserShiftResponse.builder()
//                .id(userShift.getId())
//                .shift(shift.get().toDto())
//                .user(user.get().toDto())
//                .build();
//        res.setMessage("user add shift");
//        res.setStatus(true);
//        res.setData(userShiftResponse);
//
//        return ResponseEntity.ok(res);
        return null;
    }

    @Override
    public ResponseEntity<?> deleteById(long id) {
//        Optional<UserShift> userShiftOptional = userShiftRepository.findById(id);
//        if (userShiftOptional.isPresent()) {
//            userShiftRepository.deleteById(id);
//            res.setMessage("User shift deleted successfully");
//            res.setStatus(true);
//            return ResponseEntity.ok(res);
//        } else {
//            res.setMessage("User shift not found");
//            res.setStatus(false);
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
//        }
        return null;
    }

    @Override
    public ResponseEntity<?> deleteByUserShift(UserShiftRequest userShiftRequest) {
//        if (userShiftRepository.existsByUserIdAndShiftId(
//                userShiftRequest.getUser(),
//                userShiftRequest.getShift())) {
//            userShiftRepository.deleteByUserIdAndShiftId(
//                    userShiftRequest.getUser(),
//                    userShiftRequest.getShift());
//            res.setMessage("User shift deleted successfully");
//            res.setStatus(true);
//            return ResponseEntity.ok(res);
//        } else {
//            res.setMessage("User shift not found");
//            res.setStatus(false);
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
//        }
        return null;
    }
}
