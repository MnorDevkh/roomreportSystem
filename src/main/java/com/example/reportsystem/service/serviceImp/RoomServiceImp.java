package com.example.reportsystem.service.serviceImp;

import com.example.reportsystem.model.Room;
import com.example.reportsystem.model.request.RoomRequest;
import com.example.reportsystem.model.responses.ApiResponse;
import com.example.reportsystem.repository.RoomRepository;
import com.example.reportsystem.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImp implements RoomService {
    private final RoomRepository roomRepository;

    public RoomServiceImp(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
    @Override
    public ResponseEntity<?> findAll() {
        List<Room> roomResponse = roomRepository.findAll();
try {
    return ResponseEntity.ok(ApiResponse.<List<Room>>builder()
            .message("room fetch successfully")
            .status(HttpStatus.OK)
            .payload(roomResponse)
            .build());
}catch (Exception e){
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse.builder()
            .message("Error fetching room")
            .status(HttpStatus.NOT_FOUND)
            .build());
        }
    }

    @Override
    public ResponseEntity<?> save(RoomRequest request) {

    Room room = null;
    room = Room.builder()
            .name(request.getName())
            .floor(request.getFloor())
            .type(request.getType())
            .description(request.getDescription())
            .build();
    try {
        Room  saveRoom = roomRepository.save(room);
        return ResponseEntity.ok(ApiResponse.<Room>builder()
                .message("room save successfully")
                .status(HttpStatus.OK)
                .payload(saveRoom)
                .build());
    }catch (Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.builder()
                        .message("Error save room")
                        .status(HttpStatus.NOT_FOUND)
                        .build());
    }

    }





}
