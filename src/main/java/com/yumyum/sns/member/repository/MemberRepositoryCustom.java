package com.yumyum.sns.member.repository;

import com.yumyum.sns.member.dto.MemberSearchDto;
import com.yumyum.sns.member.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepositoryCustom {

    //검색시 회원 정보
    Optional<MemberSearchDto> findMemberSearch(String nickName);

    //검색시 회원 프로필 미리보기
    List<Member> getPreviewByNickname(String keyword);

}
