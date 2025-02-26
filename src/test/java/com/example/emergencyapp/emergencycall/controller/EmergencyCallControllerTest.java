package com.example.emergencyapp.emergencycall.controller;

import com.example.emergencyapp.emergencycall.commands.CreateCallerDetailsCommand;
import com.example.emergencyapp.emergencycall.commands.CreateEmergencyCallCommand;
import com.example.emergencyapp.emergencycall.model.Caller;
import com.example.emergencyapp.emergencycall.model.EmergencyCall;
import com.example.emergencyapp.emergencycall.model.EmergencyType;
import com.example.emergencyapp.emergencycall.model.SeverityType;
import com.example.emergencyapp.emergencycall.repository.EmergencyCallRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class EmergencyCallControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private EmergencyCallRepository callRepository;

    @BeforeEach
    void clear(){
        callRepository.deleteAll();
    }

    @Test
    public void addCallShouldReturn201WhenDataIsValid() throws Exception {
        //given
        CreateEmergencyCallCommand cmd = new CreateEmergencyCallCommand();
        cmd.setCallerDetails(new CreateCallerDetailsCommand("John", "Doe", "555-1234"));
        cmd.setLocation("40.712776,-74.005974");
        cmd.setEmergencyType(EmergencyType.FIRE);
        cmd.setSeverityType(SeverityType.HIGH);
        cmd.setTime(LocalDateTime.of(2025, 2, 23, 15, 0));
        //when
        mockMvc.perform(post("/api/v1/emergency-call")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cmd)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.emergencyType").value("FIRE"))
                .andExpect(jsonPath("$.severityType").value("HIGH"));
    }

    @Test
    public void addCallShouldReturn400WhenRequiredFieldIsMissing() throws Exception {
        //given
        CreateEmergencyCallCommand cmd = new CreateEmergencyCallCommand();
        cmd.setCallerDetails(new CreateCallerDetailsCommand("Alice", "Wonder", "555-9999"));
        cmd.setEmergencyType(EmergencyType.MEDICAL);
        cmd.setSeverityType(SeverityType.LOW);
        //when
        mockMvc.perform(post("/api/v1/emergency-call")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cmd)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addCallShouldReturn400WhenJsonIsInvalid() throws Exception {
        //given
        String invalidJson = "{ \"callerDetails\": { \"name\": \"Bob\" ";
        //when
        mockMvc.perform(post("/api/v1/emergency-call")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getCallsByCriteriaShouldReturnOkWhenNoCriteriaProvided() throws Exception {
        //when
        mockMvc.perform(get("/api/v1/emergency-call")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    public void getCallsByCriteriaShouldReturnPageWhenValidCriteria() throws Exception {
        //given
        List<EmergencyCall> calls = List.of(
                new EmergencyCall(new Caller("John", "Doe", "555-1234"),
                        "40.712776,-74.005974",
                        EmergencyType.FIRE,SeverityType.HIGH,
                        LocalDateTime.of(2025, 2, 23, 15, 0)),
                new EmergencyCall(new Caller("Alice", "Wonder", "555-5678"),
                        "51.507351,-0.127758",
                        EmergencyType.POLICE, SeverityType.MEDIUM,
                        LocalDateTime.of(2025, 2, 25, 10, 30))
        );
        callRepository.saveAllAndFlush(calls);

        Map<String, String> criteria = new HashMap<>();
        criteria.put("severityType", SeverityType.HIGH.toString());
        //when
        mockMvc.perform(get("/api/v1/emergency-call")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(criteria)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].location").value("40.712776,-74.005974"))
                .andExpect(jsonPath("$.content[0].severityType").value("HIGH"));

    }


}