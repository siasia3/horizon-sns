package com.yumyum.sns.friend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendRequestPostDto {

    @NotNull
    private Long senderId;
    @NotNull
    private Long receiverId;
}
