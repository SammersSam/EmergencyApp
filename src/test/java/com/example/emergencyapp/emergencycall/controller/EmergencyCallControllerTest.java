package com.example.emergencyapp.emergencycall.controller;

import com.example.emergencyapp.emergencycall.commands.CreateCallerDetailsCommand;
import com.example.emergencyapp.emergencycall.commands.CreateEmergencyCallCommand;
import com.example.emergencyapp.emergencycall.model.EmergencyType;
import com.example.emergencyapp.emergencycall.model.SeverityType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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


    @Test
    public void testAddCall_Success() throws Exception {
        //given
        CreateEmergencyCallCommand command = new CreateEmergencyCallCommand();
        command.setCallerDetails(new CreateCallerDetailsCommand("Jan", "Kowalski","123455"));
        command.setLocation("New York");
        command.setEmergencyType(EmergencyType.FIRE);
        command.setSeverityType(SeverityType.HIGH);
        command.setTime(java.time.LocalDateTime.now());

        //when
        String requestJson = objectMapper.writeValueAsString(command);

        //then
        mockMvc.perform(post("/api/v1/emergency-call")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated());
    }


}