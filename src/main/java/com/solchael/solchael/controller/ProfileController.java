package com.solchael.solchael.controller;

import com.solchael.solchael.service.MemberService;
import com.solchael.solchael.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Tag(name = "사용자 프로필 관련 컨트롤러")
public class ProfileController {

    private final ProfileService profileService;
    private final MemberService memberService;

    @Operation(summary = "사용자 프로필 등록 API")
    @PatchMapping(value = "/api/v1/member/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateMemberInfo(@RequestPart(name = "file") MultipartFile file, HttpSession session) throws IOException {

        Long memberId = (Long) session.getAttribute("memberId");
        String downloadUrl = profileService.updateMemberInfo(memberId, file);

        return new ResponseEntity<>(downloadUrl, HttpStatus.OK);
    }
}
