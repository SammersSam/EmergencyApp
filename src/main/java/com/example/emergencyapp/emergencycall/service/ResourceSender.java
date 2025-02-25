package com.example.emergencyapp.emergencycall.service;

import com.example.emergencyapp.emergencycall.model.EmergencyResource;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@RequiredArgsConstructor

public class ResourceSender {

//    @Override
//    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        session.sendMessage(new TextMessage("hello"));
//    }


    private final SimpMessagingTemplate messagingTemplate;

    public void dispatchResources(EmergencyResource resource){
        System.out.println("websocket" + resource.getId());
        messagingTemplate.convertAndSend("/topic/resource-dispatch",resource);
    }
//    @MessageMapping("/ws")
//    @SendTo("topic/resource")
//    public String dispatchResources(EmergencyResource resource){
//        return"test";
//    }
}
