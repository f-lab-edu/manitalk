package com.example.web.controller;

import com.example.web.dto.*;
import com.example.web.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<SendMessageResponse> sendMessage(@Valid @RequestBody SendMessageRequest dto) {
        return new ResponseEntity<>(messageService.sendMessage(dto), HttpStatus.OK);
    }
}
