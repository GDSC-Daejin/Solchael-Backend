package com.solchael.solchael.service;

import com.solchael.solchael.dto.LoginRequest;
import com.solchael.solchael.dto.JoinRequest;
import com.solchael.solchael.entity.Member;
import com.solchael.solchael.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
            throw new RuntimeException("존재하지 않는 회원이거나 비밀번호가 일치하지 않습니다.");
        }

        return member;
    }
}
