package com.example.emergencyapp.controller;

import com.example.emergencyapp.commands.CreateEmergencyCallCommand;
import com.example.emergencyapp.model.EmergencyCall;
import com.example.emergencyapp.model.dto.EmergencyCallDto;
import com.example.emergencyapp.service.EmergencyCallService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/emergency-call")
@RequiredArgsConstructor
public class EmergencyCallController {

    private final ModelMapper mapper;
    private final EmergencyCallService service;

    @PostMapping
    public ResponseEntity<EmergencyCallDto> addCall(@RequestBody CreateEmergencyCallCommand command){
        EmergencyCall emergencyCall = mapper.map(command, EmergencyCall.class);
        EmergencyCall savedCall = service.save(emergencyCall);
        EmergencyCallDto callDto = mapper.map(savedCall, EmergencyCallDto.class);
        return ResponseEntity.ok(callDto);
    }
}
