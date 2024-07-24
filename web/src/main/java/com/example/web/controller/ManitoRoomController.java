package com.example.web.controller;

import com.example.web.dto.*;
import com.example.web.service.ManitoRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manito/room")
@RequiredArgsConstructor
@Tag(name = "Manito Room", description = "마니또 채팅방 API")
public class ManitoRoomController {

    private final ManitoRoomService manitoRoomService;

    @Operation(summary = "마니또 채팅방 생성")
    @PostMapping
    public ResponseEntity<CreateManitoRoomResponse> createManitoRooms(@Valid @RequestBody CreateManitoRoomRequest dto) {
        return new ResponseEntity<>(manitoRoomService.createManitoRooms(dto), HttpStatus.OK);
    }

    @Operation(summary = "마니또 채팅방 입장")
    @PostMapping("/enter")
    public ResponseEntity<EnterManitoRoomResponse> enterManitoRoom(@Valid @RequestBody EnterManitoRoomRequest dto) {
        return new ResponseEntity<>(manitoRoomService.enterManitoRoom(dto), HttpStatus.OK);
    }
}
