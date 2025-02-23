package com.example.emergencyapp.emergencycall.service;

import com.example.emergencyapp.emergencycall.model.EmergencyCall;
import com.example.emergencyapp.emergencycall.repository.EmergencyCallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DispatchService {

    private final EmergencyCallRepository callRepo;
    private final EmergencyResourcesService resourcesService;

    @Scheduled(fixedDelayString = "30000")
    public void getCallsToProcess() {
        List<EmergencyCall> calls = callRepo.
                findAllByDispatchedFalseOrderBySeverityTypeDescTimeAsc(Pageable.ofSize(20));
        for (EmergencyCall call : calls) {
            resourcesService.processCall(call.getId());
        }
    }
}
