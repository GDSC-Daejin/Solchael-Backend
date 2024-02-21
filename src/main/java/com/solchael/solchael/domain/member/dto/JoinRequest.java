package com.solchael.solchael.domain.member.dto;

import com.solchael.solchael.domain.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class JoinRequest {

    @NotEmpty(message = "닉네임은 필수입니다.")
    @Size(min = 2, max = 10, message = "닉네임은 2자에서 10자 사이여야 합니다.")
    private String nickName;

    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 4, max = 12, message = "아이디는 4자에서 12자 사이여야 합니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자에서 20자 사이여야 합니다.")
    private String password;

    // Entity -> Dto
    public static JoinRequest toEntity(Member member) {
        return JoinRequest.builder()
                .nickName(member.getNickName())
                .email(member.getEmail())
                .password(member.getPassword())
                .build();
    }
}
