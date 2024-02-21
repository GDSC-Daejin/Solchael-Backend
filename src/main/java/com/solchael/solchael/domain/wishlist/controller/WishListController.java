package com.solchael.solchael.domain.wishlist.controller;

import com.solchael.solchael.domain.wishlist.dto.WishListDto;
import com.solchael.solchael.domain.wishlist.service.WishListService;
import io.swagger.v3.oas.annotations.Operation;
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
    @PostMapping("/api/v1/mypage/wish/{medicineId}")
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
