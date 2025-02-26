package com.example.emergencyapp.emergencycall.controller;

import com.example.emergencyapp.emergencycall.model.EmergencyResource;
import com.example.emergencyapp.emergencycall.model.dto.EmergencyResourceDto;
import com.example.emergencyapp.emergencycall.service.EmergencyResourcesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/emergency-resource")
@RequiredArgsConstructor
@Tag(name = "Emergency Resources", description = "Endpoint for dispatching resources")
public class EmergencyResourceController {

    private final EmergencyResourcesService resourcesService;
    private final ModelMapper mapper;

    @Operation(
            summary = "Get assigned resources",
            description = "Retrieves a list of assigned resources."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of resources"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Parameter(description = "Number of resources to fetch", required = true, example = "10")
    @GetMapping
    public ResponseEntity<List<EmergencyResourceDto>> getAssignedResources(
            @RequestParam("amountOfResources") int amountOfResources) {
        return ResponseEntity.ok(resourcesService.getAssignedResources(amountOfResources)
                .stream()
                .map(x -> mapper.map(x, EmergencyResourceDto.class))
                .toList());
    }

}
