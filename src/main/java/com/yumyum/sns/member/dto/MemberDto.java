package com.yumyum.sns.member.dto;

import com.yumyum.sns.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberDto {
    private Long id;

    public MemberDto(Long id) {
        this.id = id;
    }
}
