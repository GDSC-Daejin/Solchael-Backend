package com.solchael.solchael.controller;

import com.solchael.solchael.dto.WishListDto;
import com.solchael.solchael.service.WishListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "위시 리스트 관련 컨트롤러")
public class WishListController {

    private final WishListService wishListService;

    @Operation(summary = "위시 리스트에 알약 등록 API")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "성공적으로 등록되었습니다."),
            @ApiResponse(responseCode = "500", description = "세션이 존재하지 않습니다. 로그인 후 진행하세요")
    })
    @PostMapping("/api/v1/mypage/register/wish/{medicineId}")
    public ResponseEntity getMyWish(@PathVariable(name = "medicineId") Long medicineId, HttpSession session) {

        Long memberId = (Long) session.getAttribute("memberId");
        wishListService.registerWishList(medicineId, memberId);

        return ResponseEntity.ok("위시 리스트에 등록 완료");
    }

    @Operation(summary = "위시 리스트 전체 조회 API")
    @GetMapping("/api/v1/mypage/wishlists")
    public ResponseEntity<List<WishListDto>> getWishLists(HttpSession session) {

        Long memberId = (Long) session.getAttribute("memberId");
        List<WishListDto> myWishLists = wishListService.getMyWishLists(memberId);

        return new ResponseEntity<>(myWishLists, HttpStatus.OK);
    }

    @Operation(summary = "위시 리스트 삭제 API")
    @DeleteMapping("/api/v1/mypage/wishlists/{medicineId}")
    public ResponseEntity deleteMyWish(@PathVariable(name = "medicineId") Long medicineId, HttpSession session) {

        Long memberId = (Long) session.getAttribute("memberId");
        wishListService.deleteMyWish(medicineId, memberId);

        return ResponseEntity.ok("삭제 완료");
    }
}
