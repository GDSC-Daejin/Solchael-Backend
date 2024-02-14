package com.solchael.solchael.domain.drugbox.api;

import com.solchael.solchael.domain.drugbox.api.service.KakaoAPIService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "카카오 API 관련 컨트롤러")
public class KakaoAPIController {

    private final KakaoAPIService kakaoAPIService;

    @Operation(summary = "주소 -> 좌표로 변환해서 DB에 반영해주는 API ( 사용하면 안 돼요 )")
    @GetMapping("/api/v1/loadCoordinate")
    public void loadKakaoApi() {
        kakaoAPIService.getCoordinate();
    }
}
