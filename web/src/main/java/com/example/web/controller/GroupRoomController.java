package com.example.web.controller;

import com.example.web.dto.*;
import com.example.web.service.GroupRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group/room")
@RequiredArgsConstructor
public class GroupRoomController {

    private final GroupRoomService groupRoomService;

    @PostMapping
    public ResponseEntity<CreateGroupRoomResponse> createGroupRoom(@Valid @RequestBody CreateGroupRoomRequest dto) {
        return new ResponseEntity<>(groupRoomService.createGroupRoom(dto), HttpStatus.OK);
    }

    @PostMapping("/enter")
    public ResponseEntity<EnterRoomResponse> enterGroupRoom(@Valid @RequestBody EnterGroupRoomRequest dto) {
        return new ResponseEntity<>(groupRoomService.enterGroupRoom(dto), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<EndRoomResponse> endGroupRoom(@Valid @RequestBody EndGroupRoomRequest dto) {
        return new ResponseEntity<>(groupRoomService.endGroupRoom(dto), HttpStatus.OK);
    }
}
