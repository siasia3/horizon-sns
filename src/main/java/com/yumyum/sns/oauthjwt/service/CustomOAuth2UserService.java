package com.yumyum.sns.oauthjwt.service;


import com.yumyum.sns.member.entity.Member;
import com.yumyum.sns.member.repository.MemberRepository;
import com.yumyum.sns.member.service.MemberService;
import com.yumyum.sns.oauthjwt.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberService memberService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        //registrationId 가져오기
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response;
        if(registrationId.equals("naver")){
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }else if(registrationId.equals("google")){
            oAuth2Response = new GoogleReponse(oAuth2User.getAttributes());
        }else{
            return null;
        }

        //리소스 서버에서 발급 받은 정보로 사용자를 특정할 아이디값을 만듬
        String identifier = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();

        Optional<Member> existData = memberService.checkIdentifier(identifier);


        if(!existData.isPresent()) {

            String nickname = generateTemporaryNickname(oAuth2Response.getProvider());

            while(memberService.checkNicknameDuplicate(nickname)){
                nickname = generateTemporaryNickname(oAuth2Response.getProvider());
            }
            Member Member = new Member(identifier,oAuth2Response.getName(),nickname, oAuth2Response.getEmail(), "ROLE_USER");
            Member savedMember = memberService.createMember(Member);

            UserDTO userDTO = new UserDTO("ROLE_USER",oAuth2Response.getName(),identifier);

            return new CustomOAuth2User(userDTO);
        }else{
            Member member = existData.get();

            member.checkPersonalInfo(oAuth2Response.getName(),oAuth2Response.getEmail());

            memberService.modifyMember(member);

            UserDTO userDTO = new UserDTO(member.getRole(),oAuth2Response.getName(), member.getIdentifier());

            return new CustomOAuth2User(userDTO);
        }
    }

    //임시닉네임 생성
    private String generateTemporaryNickname(String provider) {
        int randomNum = (int)(Math.random() * 1_000_000);
        String formatted = String.format("%06d", randomNum);
        return provider.toLowerCase() + formatted; // 예시: google123456, naver123456
    }
}
