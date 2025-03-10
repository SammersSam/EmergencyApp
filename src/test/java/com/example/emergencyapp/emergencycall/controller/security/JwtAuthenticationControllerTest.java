package com.example.emergencyapp.emergencycall.controller.security;

import com.example.emergencyapp.security.JwtRequest;
import com.example.emergencyapp.security.Role;
import com.example.emergencyapp.security.User;
import com.example.emergencyapp.security.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class JwtAuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoSpyBean
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private PasswordEncoder encoder;


    @Test
    public void shouldCreateAuthenticationToken() throws Exception {
        //given
        userRepository.saveAndFlush(new User("Jan", "jan@wp.pl",
                encoder.encode("jan123"), Role.ADMIN));
        JwtRequest request = new JwtRequest("Jan", "jan123");
        //when
        mockMvc.perform(post("/authenticate")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwtToken").isNotEmpty());
        //then
        verify(userRepository, times(2)).findByUserName(request.getUserName());
    }

    @Test
    public void shouldReturn401WhenRequestIsIncorrect() throws Exception {
        //given
        userRepository.saveAndFlush(new User("Piotr", "piotr@wp.pl",
                encoder.encode("piotr123"), Role.ADMIN));
        JwtRequest request = new JwtRequest("Piotr", "123");
        //when
        mockMvc.perform(post("/authenticate")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isUnauthorized());

        //then
        verify(userRepository, times(1)).findByUserName(request.getUserName());
    }



}