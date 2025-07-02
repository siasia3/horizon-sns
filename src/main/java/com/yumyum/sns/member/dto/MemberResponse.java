package com.yumyum.sns.member.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberResponse {
    public Long memberId;
    public String nickname;
    public String profilePath;

    public MemberResponse(Long memberId, String nickname, String profilePath) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.profilePath = profilePath;
    }
}
