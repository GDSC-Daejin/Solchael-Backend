package com.solchael.solchael.domain.member.service;

import com.solchael.solchael.domain.member.dto.JoinRequest;
import com.solchael.solchael.domain.member.dto.LoginRequest;
import com.solchael.solchael.domain.member.entity.Member;
import com.solchael.solchael.global.exception.RestApiException;
import com.solchael.solchael.global.exception.UserErrorCode;
import com.solchael.solchael.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public JoinRequest join(JoinRequest joinRequest) {
        Member member = Member.createMember(joinRequest);
        memberRepository.save(member);
        return JoinRequest.fromEntity(member);
    }

    /**
     * 중복 체크
     */
    public boolean validateDuplicateNickName(String nickName) {
        Member findNickName = memberRepository.findByName(nickName);

        if (findNickName != null) {
            throw new RestApiException(UserErrorCode.CONFLICT_NAME);
        } else {
            return true;
        }
    }

    public boolean validateDuplicateEmail(String email) {
        Member findEmail = memberRepository.findByEmail(email);

        if (findEmail != null) {
            throw new RestApiException(UserErrorCode.CONFLICT_EMAIL);
        } else {
            return true;
        }
    }

    public Member signIn(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(loginRequest.getEmail());

        if (member == null || !member.getPassword().equals(loginRequest.getPassword())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 회원이거나 비밀번호가 일치하지 않습니다.");
        }

        return member;
    }

}