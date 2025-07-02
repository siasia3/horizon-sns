package com.yumyum.sns.member.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MemberSearchDto {
    public Long memberId;
    public String nickName;
    public String profilePath;
    public Long friendCnt;
    public Long postCnt;

    @QueryProjection
    public MemberSearchDto(Long memberId, String nickName, String profilePath, Long friendCnt, Long postCnt) {
        this.memberId = memberId;
        this.nickName = nickName;
        this.profilePath = profilePath;
        this.friendCnt = friendCnt;
        this.postCnt = postCnt;
    }
}
