package com.example.web.controller;

import com.example.web.dto.CreateGroupRoomDto;
import com.example.web.dto.EnterGroupRoomDto;
import com.example.web.dto.UserRoomDto;
import com.example.web.service.GroupRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.web.dto.GroupRoomDto;

@RestController
@RequestMapping("/group/room")
@RequiredArgsConstructor
public class GroupRoomController {

    private final GroupRoomService groupRoomService;

    @PostMapping
    public ResponseEntity<GroupRoomDto> createGroupRoom(@Valid @RequestBody CreateGroupRoomDto dto) {
        return new ResponseEntity<>(groupRoomService.createGroupRoom(dto), HttpStatus.OK);
    }

    @PostMapping("/enter")
    public ResponseEntity<UserRoomDto> enterGroupRoom(@Valid @RequestBody EnterGroupRoomDto dto) {
        return new ResponseEntity<>(groupRoomService.enterGroupRoom(dto), HttpStatus.OK);
    }
}
