package com.example.emergencyapp.emergencycall.controller;

import com.example.emergencyapp.emergencycall.exceptions.NotAvailableResourcesExceptions;
import com.example.emergencyapp.emergencycall.model.*;
import com.example.emergencyapp.emergencycall.repository.EmergencyCallRepository;
import com.example.emergencyapp.emergencycall.repository.EmergencyResourcesRepository;
import com.example.emergencyapp.emergencycall.service.EmergencyResourcesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.Assert.*;
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class EmergencyResourceControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private EmergencyCallRepository callRepository;
    @Autowired
    private EmergencyResourcesRepository resourcesRepository;

    @BeforeEach
    void clear(){
        resourcesRepository.deleteAll();
    }

    @Test
    public void shouldReturnOkAndListWhenResourcesFound() throws Exception {
        //given
        List<EmergencyResource> resources = List.of(
                new EmergencyResource(ResourcesType.AMBULANCE,
                        ResourcesStatusType.AVAILABLE, "51.5074,-0.1278",null),

                new EmergencyResource(ResourcesType.FIRE_TRUCK, ResourcesStatusType.AVAILABLE,
                        "40.7128,-74.0060",null)
        );
        List<EmergencyCall> calls = List.of(
                new EmergencyCall(new Caller("John", "Doe", "555-1234"),
                        "40.712776,-74.005974",
                        EmergencyType.FIRE,SeverityType.HIGH,
                        LocalDateTime.of(2025, 2, 23, 15, 0)),
                new EmergencyCall(new Caller("Alice", "Wonder", "555-5678"),
                        "51.507351,-0.127758",
                        EmergencyType.POLICE, SeverityType.MEDIUM,
                        LocalDateTime.of(2025, 2, 25, 10, 30)));
        resourcesRepository.saveAllAndFlush(resources);
        callRepository.saveAllAndFlush(calls);
        //when
        mockMvc.perform(get("/api/v1/emergency-resource")
                .param("amountOfResources", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].type").value("FIRE_TRUCK"))
                .andExpect(jsonPath("$.[0].location").value("40.7128,-74.0060"))
                .andExpect(jsonPath("$.[0].resourcesStatusType").value("BUSY"));

    }

    
    @Test
    public void shouldReturn404WhenNoResourcesFound() throws Exception {
        //given
        List<EmergencyResource> resources = List.of(
                new EmergencyResource(ResourcesType.AMBULANCE,
                        ResourcesStatusType.BUSY, "51.5074,-0.1278",null),

                new EmergencyResource(ResourcesType.FIRE_TRUCK, ResourcesStatusType.BUSY,
                        "40.7128,-74.0060",null)
        );
        List<EmergencyCall> calls = List.of(
                new EmergencyCall(new Caller("John", "Doe", "555-1234"),
                        "40.712776,-74.005974",
                        EmergencyType.FIRE,SeverityType.HIGH,
                        LocalDateTime.of(2025, 2, 23, 15, 0)),
                new EmergencyCall(new Caller("Alice", "Wonder", "555-5678"),
                        "51.507351,-0.127758",
                        EmergencyType.POLICE, SeverityType.MEDIUM,
                        LocalDateTime.of(2025, 2, 25, 10, 30)));
        resourcesRepository.saveAllAndFlush(resources);
        callRepository.saveAllAndFlush(calls);

        //when
        mockMvc.perform(get("/api/v1/emergency-resource")
                .param("amountOfResources", "10"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Lack of available resources!"));


    }

    
  

    
  
}

