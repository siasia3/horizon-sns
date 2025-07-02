package com.yumyum.sns.friend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendReqDto {

    private Long friendRequestId;
    @NotNull
    private Long myMemberId;
    @NotNull
    private Long friendMemberId;
}
