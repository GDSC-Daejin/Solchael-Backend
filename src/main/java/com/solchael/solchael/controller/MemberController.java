package com.solchael.solchael.controller;

import com.solchael.solchael.dto.LoginRequest;
import com.solchael.solchael.dto.JoinRequest;
import com.solchael.solchael.entity.Member;
import com.solchael.solchael.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "사용자 관련 컨트롤러")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "닉네임 중복체크 API")
    @GetMapping("/api/v1/user-nickName/{nickName}/exists")
    public ResponseEntity<Boolean> duplicateNickName(@PathVariable(name = "nickName") String nickName) {
        boolean isDuplicate = memberService.validateDuplicateNickName(nickName);
        return new ResponseEntity<>(isDuplicate, isDuplicate ? HttpStatus.OK : HttpStatus.CONFLICT);
    }

    @Operation(summary = "아이디 중복체크 API")
    @GetMapping("/api/v1/user-email/{email}/exists")
    public ResponseEntity<Boolean> duplicateEmail(@PathVariable(name = "email") String email) {
        boolean isDuplicate = memberService.validateDuplicateEmail(email);
        return new ResponseEntity<>(isDuplicate, isDuplicate ? HttpStatus.OK : HttpStatus.CONFLICT);
    }

    @Operation(summary = "회원가입 API")
    @PostMapping("/api/v1/join")
    public ResponseEntity create(@Valid @RequestBody JoinRequest joinRequest, BindingResult result) {

        if (result.hasErrors()) { // 유효성 검사
            List<String> errors = new ArrayList<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.add(error.getField() + ": " + error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        } else if (!memberService.validateDuplicateEmail(joinRequest.getEmail()) || !memberService.validateDuplicateNickName(joinRequest.getNickName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("중복 체크를 먼저 해주세요");
        }

        JoinRequest member = memberService.join(joinRequest);
        return ResponseEntity.ok(member);
    }

    @Operation(summary = "세션 로그인 API")
    @PostMapping("/api/v1/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest, HttpSession httpSession) {
        Member member = memberService.signIn(loginRequest);

        httpSession.setAttribute("memberId", member.getId());

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("sessionId", httpSession.getId());
        responseMap.put("nickname", member.getNickName());

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    @Operation(summary = "로그아웃 API")
    @PostMapping("/api/v1/logout")
    public ResponseEntity<String> logout(HttpSession httpSession) {
        httpSession.invalidate();
        return new ResponseEntity<>("로그아웃 성공", HttpStatus.OK);
    }
}
