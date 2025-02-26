package com.example.emergencyapp.emergencycall.controller;

import com.example.emergencyapp.emergencycall.commands.CreateEmergencyCallCommand;
import com.example.emergencyapp.emergencycall.model.EmergencyCall;
import com.example.emergencyapp.emergencycall.model.dto.EmergencyCallDto;
import com.example.emergencyapp.emergencycall.service.EmergencyCallService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/emergency-call")
@RequiredArgsConstructor
@Tag(name = "Emergency Calls", description = "Endpoints for managing emergency calls")
public class EmergencyCallController {

    private final ModelMapper mapper;
    private final EmergencyCallService service;

    @Operation(
            summary = "Create a new emergency call",
            description = "Creates a new emergency call with the provided details and returns the created call information."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<EmergencyCallDto> addCall(@RequestBody @Valid CreateEmergencyCallCommand command) {
        EmergencyCall emergencyCall = mapper.map(command, EmergencyCall.class);
        EmergencyCall savedCall = service.save(emergencyCall);
        EmergencyCallDto callDto = mapper.map(savedCall, EmergencyCallDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(callDto);
    }

    @Operation(
            summary = "Search emergency calls by criteria",
            description = "Search emergency calls by various criteria. For range-based attributes, " +
                    "include two keys: one ending with 'From' and one ending with 'To'. ")
    @Parameter(
            description = "A JSON object containing search criteria. For range queries, use keys ending in 'From' and 'To'. " +
                    "Example: { \"timeFrom\": \"2023-01-01T00:00:00\", \"timeTo\": \"2023-01-31T23:59:59\", " +
                    "\"location\": \"37.774929,-122.419418\", \"emergencyType\": \"FIRE\", \"callerDetails.name\": \"John\" }")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Returns a paginated list of emergency calls. If no data exists, an empty page is returned."),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<Page<EmergencyCallDto>> getCallsByCriteria(@RequestBody Map<String, String> criteria,
                                                                     @PageableDefault Pageable pageable) {
        Page<EmergencyCall> emergencyCallsPage = service.getByCriteria(criteria, pageable);
        return ResponseEntity.ok(emergencyCallsPage
                .map(x -> mapper.map(x, EmergencyCallDto.class)));
    }
}
