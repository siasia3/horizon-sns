package com.yumyum.sns.friend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FriendRequestReqDto {

    @NotNull
    private Long myId;
    @NotNull
    private Long userId;
}
