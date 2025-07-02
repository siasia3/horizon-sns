package com.yumyum.sns.friend.dto;

import com.yumyum.sns.friend.entity.FriendRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class FriendRequestResDto {
    private Long friendRequestId;
    private Long senderId;
    private Long receiverId;
    private String state;

    public FriendRequestResDto(Long friendRequestId, Long senderId, Long receiverId,String status) {
        this.friendRequestId = friendRequestId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.state = status;
    }

    public static FriendRequestResDto toDto(FriendRequest friendRequest){
        return FriendRequestResDto.builder()
                .friendRequestId(friendRequest.getId())
                .senderId(friendRequest.getRequester().getId())
                .receiverId(friendRequest.getReceiver().getId())
                .state(friendRequest.getState().name())
                .build();
    }
}
