package com.solchael.solchael.service;

import com.solchael.solchael.dto.LoginRequest;
import com.solchael.solchael.dto.JoinRequest;
import com.solchael.solchael.entity.Member;
import com.solchael.solchael.repository.MemberRepository;
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
        return findNickName == null;
    }

    public boolean validateDuplicateEmail(String email) {
        Member findEmail = memberRepository.findByEmail(email);
        return findEmail == null;
    }

    public Member signIn(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(loginRequest.getEmail());

        if (member == null || !member.getPassword().equals(loginRequest.getPassword())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 회원이거나 비밀번호가 일치하지 않습니다.");
        }

        return member;
    }

    /**
     * 마이 페이지 관련 - 본인 약 등록, 원하는 약 저장 ( 위시 리스트 느낌 )
     */

}
