package com.yumyum.sns.member.dto;

import com.yumyum.sns.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class MemberEditResponse {

    private Long memberId;
    private String name;
    private String memberProfilePath;
    private String nickname;
    private String gender;
    private LocalDate birthdate;

    public static MemberEditResponse toDto(Member member){
        return MemberEditResponse.builder()
                .memberId(member.getId())
                .name(member.getName())
                .memberProfilePath(member.getProfileImage())
                .nickname(member.getNickname())
                .gender(member.getGender())
                .birthdate(member.getBirthdate())
                .build();
    }
}
