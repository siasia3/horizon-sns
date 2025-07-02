package com.yumyum.sns.member.service;

import com.yumyum.sns.member.dto.MemberEditDto;
import com.yumyum.sns.member.dto.MemberEditResponse;
import com.yumyum.sns.member.dto.MemberSearchDto;
import com.yumyum.sns.member.dto.NicknamePreviewDto;
import com.yumyum.sns.member.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    //id값으로 회원 확인
    public Boolean checkMember(Long id);

    //식별자로 회원 조회
    public Member getMemberByIdentifier(String identifier);

    /**
     * ID로 회원 조회
     * @param memberId 회원ID
     * @return 회원 엔티티
     */
    Member getMemberById(Long memberId);

    //닉네임 회원 조회
    public Member getMemberByNickname(String nickname);

    /**
     * 닉네임 중복 체크
     * @param nickname 체크할 닉네임
     * @return 중복인 경우 true, 중복이 아닌 경우 false
     */
    public boolean checkNicknameDuplicate(String nickname);

    //식별자로 회원 확인
    public Optional<Member> checkIdentifier(String identifier);

    //소셜로그인 회원 등록
    public Member createMember(Member member);

    //회원 수정
    public Member modifyMember(Member member);

    //검색 시 회원 정보 조회
    public MemberSearchDto getSearchMember(String nickName);

    //회원 검색 미리보기
    public List<NicknamePreviewDto> previewUserByNickname(String keyword);

    /**
     * 회원 프로필편집 정보 조회
     * @param memberId 회원 Id
     * @return 편집 회원정보 dto
     */
    public MemberEditDto getMemberEditInfo(Long memberId);

    /**
     * 회원 프로필 수정
     * @param memberEditDto 회원 프로필 수정시킬 정보
     * @return 수정된 프로필 정보
     */
    public MemberEditResponse EditMemberInfo(MemberEditDto memberEditDto);

}
