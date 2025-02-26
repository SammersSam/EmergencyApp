package com.example.emergencyapp.emergencycall.controller;

import com.example.emergencyapp.emergencycall.exceptions.NotAvailableResourcesExceptions;
import com.example.emergencyapp.emergencycall.model.EmergencyCall;
import com.example.emergencyapp.emergencycall.model.EmergencyResource;
import com.example.emergencyapp.emergencycall.model.ResourcesStatusType;
import com.example.emergencyapp.emergencycall.model.ResourcesType;
import com.example.emergencyapp.emergencycall.service.EmergencyResourcesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class EmergencyResourceControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private EmergencyResourcesService resourcesService

    @Test
    public void shouldReturnOkAndListWhenResourcesFound() throws Exception {
        //given
        List<EmergencyResource> resources = List.of(
                new EmergencyResource(1L, ResourcesType.AMBULANCE, ResourcesStatusType.BUSY, "51.5074,-0.1278",new EmergencyCall(
                        
                )),
                new EmergencyResource(2L, ResourcesType.FIRE_TRUCK, ResourcesStatusType.BUSY, "40.7128,-74.0060")
        );

        //when
        when(resourcesService.getAssignedResources(5)).thenReturn(resources);

        mockMvc.perform(get("/api/emergency-resource")
                .param("amountOfResources", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$.[0].resourceType").value("AMBULANCE"))
                .andExpect(jsonPath("$.[1].location").value("40.7128,-74.0060"));
        //then
        verify(resourcesService).getAssignedResources(5);
    }

    
    @Test
    public void shouldReturn404WhenNoResourcesFound() throws Exception {
        //given
        doThrow(new NotAvailableResourcesExceptions("Lack of available resources!"))
                .when(resourcesService).getAssignedResources(10);
        //when
        mockMvc.perform(get("/api/emergency-resource")
                .param("amountOfResources", "10"))
                .andExpect(status().isNotFound());
        //then
        verify(resourcesService).getAssignedResources(10);
    }

    
  

    
  
}

