package com.yumyum.sns.chat.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DirectChatMemRequest {
    @NotNull(message = "보내는 사람 ID는 필수입니다.")
    private Long senderId;
    @NotNull(message = "받는 사람 ID는 필수입니다.")
    private Long receiverId;

    public List<Long> chatRoomMems = new ArrayList<>();

    public DirectChatMemRequest(Long senderId, Long receiverId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        chatRoomMems = Arrays.asList(senderId, receiverId);
    }

    public void setChatRoomMems() {
        if (senderId != null && receiverId != null) {
            chatRoomMems = Arrays.asList(senderId, receiverId);
        }
    }
}
