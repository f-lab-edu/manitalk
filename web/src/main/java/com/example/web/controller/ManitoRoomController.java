package com.example.web.controller;

import com.example.web.dto.*;
import com.example.web.service.ManitoRoomService;
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
public class ManitoRoomController {

    private final ManitoRoomService manitoRoomService;

    @PostMapping
    public ResponseEntity<CreateManitoRoomResponse> createManitoRooms(@Valid @RequestBody CreateManitoRoomRequest dto) {
        return new ResponseEntity<>(manitoRoomService.createManitoRooms(dto), HttpStatus.OK);
    }

    @PostMapping("/enter")
    public ResponseEntity<EnterManitoRoomResponse> enterManitoRoom(@Valid @RequestBody EnterManitoRoomRequest dto) {
        return new ResponseEntity<>(manitoRoomService.enterManitoRoom(dto), HttpStatus.OK);
    }
}
