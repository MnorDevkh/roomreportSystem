package com.example.reportsystem.service.serviceImp;

import com.example.reportsystem.model.report.Room;
import com.example.reportsystem.model.request.RoomRequest;
import com.example.reportsystem.model.responses.RoomResponse;
import com.example.reportsystem.repository.report.RoomRepository;
import com.example.reportsystem.service.RoomService;
import com.example.reportsystem.utilities.response.EmptyObject;
import com.example.reportsystem.utilities.response.Message;
import com.example.reportsystem.utilities.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImp implements RoomService {
    private final RoomRepository roomRepository;
    ResponseObject res = new ResponseObject();
    Message message = new Message();
    EmptyObject emptyObject = new EmptyObject();

    @Override
    public ResponseEntity<?> findAll() {
        List<Room> roomList = roomRepository.findAll();
        List<RoomResponse> roomResponsesList  = new ArrayList<>();
        for (Room room : roomList) {
            if(!room.isStatus()){
                // Create RoomResponse and add it to the list
                RoomResponse roomResponse = RoomResponse.builder()
                        .id(room.getId())
                        .name(room.getName())
                        .floor(room.getFloor())
                        .type(room.getType())
                        .description(room.getDescription())
                        .date(LocalDate.now())
                        .build();
                roomResponsesList.add(roomResponse);
            }
        }
    try {
    res.setStatus(true);
    res.setMessage(message.getSuccess("Room"));
    res.setData(roomResponsesList);
    }catch (Exception e){
    emptyObject.setStatus(false);
    emptyObject.setMessage(message.invalidRequest("Room"));
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(emptyObject);
    }
    return ResponseEntity.ok(res);
    }

    @Override
    public ResponseEntity<?> findById(Integer id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        return ResponseEntity.ok(optionalRoom);
    }

    @Override
    public ResponseEntity<?> save(RoomRequest request) {

    Room room = null;
    room = Room.builder()
            .name(request.getName())
            .floor(request.getFloor())
            .type(request.getType())
            .description(request.getDescription())
            .date(LocalDate.now())
            .build();
    try {
        Room  saveRoom = roomRepository.save(room);
        res.setStatus(true);
        res.setMessage(message.getSuccess("Room"));
        res.setData(saveRoom);

    }catch (Exception e){
        emptyObject.setStatus(false);
        emptyObject.setMessage(message.invalidRequest("room"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(emptyObject);
    }
        return ResponseEntity.ok(res);
    }

    @Override
    public ResponseEntity<?> deleteById(Integer id) {
        Optional<Room> room = roomRepository.findById(id);
        if(room.isPresent()){
            Room roomObj =Room.builder()
                    .id(room.get().getId())
                    .name(room.get().getName())
                    .type(room.get().getType())
                    .floor(room.get().getFloor())
                    .description(room.get().getDescription())
                    .date(room.get().getDate())
                    .deleteAtDate(LocalDate.now())
                    .status(true)
                    .build();
            roomRepository.save(roomObj);
        }
        res.setStatus(true);
        res.setMessage("Id" + room.get().getId()+ "success!");

        return ResponseEntity.ok(res);
    }

    @Override
    public ResponseEntity<?> updateById(RoomRequest request, Integer id) {
        Optional<Room> room = roomRepository.findById(id);
        if(room.isPresent()){
            Room roomObj =Room.builder()
                    .id(room.get().getId())
                    .name(request.getName())
                    .type(request.getType())
                    .floor(request.getFloor())
                    .description(request.getDescription())
                    .date(room.get().getDate())
                    .build();
            roomRepository.save(roomObj);
            RoomResponse roomResponse = RoomResponse.builder()
                    .id(roomObj.getId())
                    .name(roomObj.getName())
                    .type(roomObj.getType())
                    .floor(roomObj.getFloor())
                    .description(roomObj.getDescription())
                    .date(roomObj.getDate())
                    .build();
            res.setMessage("room fetch success");
            res.setStatus(true);
            res.setData(roomResponse);
        }
        return ResponseEntity.ok(res);
    }


}
