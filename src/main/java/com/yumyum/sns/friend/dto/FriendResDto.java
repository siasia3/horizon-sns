package com.yumyum.sns.friend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class FriendResDto {

    private Long friendId;
    private Long friendMemId;
    private String friendProfilePath;
    private String friendNickname;

    public FriendResDto(Long friendId, Long friendMemId, String friendNickname, String friendProfilePath) {
        this.friendId = friendId;
        this.friendMemId = friendMemId;
        this.friendNickname = friendNickname;
        this.friendProfilePath = friendProfilePath;
    }

}
