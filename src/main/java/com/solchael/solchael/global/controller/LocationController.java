package com.solchael.solchael.global.controller;

import com.solchael.solchael.global.service.KakaoApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Tag(name = "위치 관련 컨트롤러")
public class LocationController {

    private final KakaoApiService kakaoApiService;

    @Operation(summary = "사용자 위치 받아오면 가까운 위치에 있는 폐의약품 수거함 조회")
    @GetMapping("/api/v1/location")
    public ResponseEntity getLocation(@RequestParam(name = "address") String address) throws IOException {
        // 받아온 위치를 기반으로 가까운 위치에 있는 좌표들을 검색해서 반환해주면 될듯
        // 일단 도로명 주소들을 좌표로 변환해서 반환해줘야 하는데.. 이게 1순위로 개발하고
        // 사용자 위치를 기반으로 가까운 위치에 있는 좌표들을 반환해주는 걸 2순위로 개발하자

        kakaoApiService.getKakaoApiFromAddress(address);

        return ResponseEntity.ok("ok");
    }
}
