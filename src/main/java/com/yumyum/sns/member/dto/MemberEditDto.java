package com.yumyum.sns.member.dto;

import com.yumyum.sns.member.entity.Member;
import com.yumyum.sns.post.dto.LikeDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberEditDto {
    @NotNull
    private Long memberId;
    private String name;
    private MultipartFile memberProfileFile;
    private String memberProfilePath;
    @Size(min = 1, max = 20, message = "닉네임은 1자 이상 20자 이하여야 합니다.")
    private String nickname;
    private String gender;
    private LocalDate birthdate;

    public static MemberEditDto toDto(Member member){
        return MemberEditDto.builder()
                .memberId(member.getId())
                .name(member.getName())
                .memberProfilePath(member.getProfileImage())
                .nickname(member.getNickname())
                .gender(member.getGender())
                .birthdate(member.getBirthdate())
                .build();
    }
}
