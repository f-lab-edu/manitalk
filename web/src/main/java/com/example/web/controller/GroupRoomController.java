package com.example.web.controller;

import com.example.web.dto.CreateGroupRoomRequest;
import com.example.web.dto.EnterGroupRoomRequest;
import com.example.web.dto.EnterRoomResponse;
import com.example.web.service.GroupRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.web.dto.CreateGroupRoomResponse;

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
}
