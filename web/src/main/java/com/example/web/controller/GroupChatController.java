package com.example.web.controller;

import com.example.web.dto.CreateGroupRoomDto;
import com.example.web.domain.Room;
import com.example.web.service.GroupChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupChatController {

    private final GroupChatService groupChatService;

    @PostMapping("/room")
    public ResponseEntity<Room> createGroupRoom(@Valid @RequestBody CreateGroupRoomDto dto) {
        return new ResponseEntity<>(groupChatService.createGroupRoom(dto), HttpStatus.OK);
    }
}
