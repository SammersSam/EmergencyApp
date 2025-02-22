package com.example.emergencyapp.emergencycall.controller;

import com.example.emergencyapp.emergencycall.commands.CreateEmergencyCallCommand;
import com.example.emergencyapp.emergencycall.model.EmergencyCall;
import com.example.emergencyapp.emergencycall.model.dto.EmergencyCallDto;
import com.example.emergencyapp.emergencycall.service.EmergencyCallService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/emergency-call")
@RequiredArgsConstructor

public class EmergencyCallController {

    private final ModelMapper mapper;
    private final EmergencyCallService service;

    @PostMapping
    public ResponseEntity<EmergencyCallDto> addCall(@RequestBody @Valid CreateEmergencyCallCommand command) {
        EmergencyCall emergencyCall = mapper.map(command, EmergencyCall.class);
        EmergencyCall savedCall = service.save(emergencyCall);
        EmergencyCallDto callDto = mapper.map(savedCall, EmergencyCallDto.class);
        return ResponseEntity.ok(callDto);
    }

    @GetMapping
    public ResponseEntity<Page<EmergencyCallDto>> getCallsByCriteria(@RequestBody Map<String, String> criteria,
                                                                     @PageableDefault Pageable pageable) {
        Page<EmergencyCall> emergencyCallsPage = service.getByCriteria(criteria, pageable);
        return ResponseEntity.ok(emergencyCallsPage
                .map(x -> mapper.map(x, EmergencyCallDto.class)));
    }
}
