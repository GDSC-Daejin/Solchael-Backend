package com.solchael.solchael.controller;

import com.solchael.solchael.service.ProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @PatchMapping(value = "/api/v1/member/{memberId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateMemberInfo(@RequestPart(name = "file") MultipartFile file
            , @PathVariable("memberId") Long memberId) throws IOException {

        String downloadUrl = profileService.updateMemberInfo(memberId, file);

        return new ResponseEntity<>(downloadUrl, HttpStatus.OK);
    }
}
