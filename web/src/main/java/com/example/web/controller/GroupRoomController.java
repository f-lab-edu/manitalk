package com.example.web.controller;

import com.example.web.dto.*;
import com.example.web.service.GroupRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group/room")
@RequiredArgsConstructor
@Tag(name = "Group Room", description = "그룹 채팅방 API")
public class GroupRoomController {

    private final GroupRoomService groupRoomService;

    @Operation(summary = "그룹 채팅방 생성")
    @PostMapping
    public ResponseEntity<CreateGroupRoomResponse> createGroupRoom(@Valid @RequestBody CreateGroupRoomRequest dto) {
        return new ResponseEntity<>(groupRoomService.createGroupRoom(dto), HttpStatus.OK);
    }

    @Operation(summary = "그룹 채팅방 입장")
    @PostMapping("/enter")
    public ResponseEntity<EnterRoomResponse> enterGroupRoom(@Valid @RequestBody EnterGroupRoomRequest dto) {
        return new ResponseEntity<>(groupRoomService.enterGroupRoom(dto), HttpStatus.OK);
    }

    @Operation(summary = "그룹 채팅방 종료")
    @DeleteMapping
    public ResponseEntity<EndRoomResponse> endGroupRoom(@Valid @RequestBody EndGroupRoomRequest dto) {
        return new ResponseEntity<>(groupRoomService.endGroupRoom(dto), HttpStatus.OK);
    }
}
