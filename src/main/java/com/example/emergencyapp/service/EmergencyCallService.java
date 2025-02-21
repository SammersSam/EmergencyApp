package com.example.emergencyapp.service;

import com.example.emergencyapp.model.EmergencyCall;
import com.example.emergencyapp.repository.EmergencyCallRepository;
import jdk.jfr.Registered;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmergencyCallService {

    private final EmergencyCallRepository repo;

    public EmergencyCall save(EmergencyCall emergencyCall){
        return repo.save(emergencyCall);
    }
}
