package com.yumyum.sns.chat.controller;

import com.yumyum.sns.chat.dto.ChatMessage;
import com.yumyum.sns.chat.dto.ChatResponse;
import com.yumyum.sns.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    @MessageMapping("/chat/send") // pub/chat/send
    public void sendMessage(ChatMessage message, Principal principal) {
        /*if (message.getType() == ChatMessage.MessageType.ENTER) {
            message.setMessage(message.getSenderId() + "님이 입장했습니다.");
        }*/
        try {
            ChatResponse chat = chatService.createChat(message);
            messagingTemplate.convertAndSend("/sub/chat/room/" + message.getChatRoomId(), chat);
        }  catch (Exception e) {
            messagingTemplate.convertAndSendToUser(
                    principal.getName(), // 유저 식별자
                    "/queue/errors",
                    "에러가 발생했습니다: " + e.getMessage()
            );
        }
    }
}
