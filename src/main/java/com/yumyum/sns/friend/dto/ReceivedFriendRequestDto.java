package com.yumyum.sns.friend.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReceivedFriendRequestDto {

    private Long friendRequestId;
    private Long senderId;
    private String profilePath;
    private Long receiverId;

    @QueryProjection
    public ReceivedFriendRequestDto(Long friendRequestId,String profilePath, Long receiverId) {
        this.friendRequestId = friendRequestId;
        this.profilePath = profilePath;
        this.receiverId = receiverId;
    }
}
