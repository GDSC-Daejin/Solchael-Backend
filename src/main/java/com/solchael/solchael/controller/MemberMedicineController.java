package com.solchael.solchael.controller;

import com.solchael.solchael.dto.*;
import com.solchael.solchael.service.MemberMedicineService;
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
@Tag(name = "나의 약 관련 컨트롤러")
public class MemberMedicineController {

    private final MemberMedicineService memberMedicineService;

    // ptp 포장이 아니니까 유통기한 입력 x -> 제조일자를 입력
    @Operation(summary = "일반 약 등록 API")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "성공적으로 등록되었습니다."),
            @ApiResponse(responseCode = "500", description = "세션이 존재하지 않습니다. 로그인 후 진행하세요")
    })
    @PostMapping("/api/v1/mypage/register/{medicineId}")
    public ResponseEntity addMedicine(@PathVariable("medicineId") Long medicineId, @RequestBody NormalMedicineDto normalMedicineDto, HttpSession session) {

        Long memberId = (Long) session.getAttribute("memberId");
        memberMedicineService.registerMedicine(medicineId, memberId, normalMedicineDto);

        return ResponseEntity.ok("나의 약 등록 완료");

    }

    // ptp 포장이니까 유통기한 입력 o -> 제조일자 입력 x
    @Operation(summary = "PTP 포장 약 등록 API")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "성공적으로 등록되었습니다."),
            @ApiResponse(responseCode = "500", description = "세션이 존재하지 않습니다. 로그인 후 진행하세요")
    })
    @PostMapping("/api/v1/mypage/register/ptp/{medicineId}")
    public ResponseEntity addPtpMedicine(@PathVariable("medicineId") Long medicineId, @RequestBody PtpMedicineDto ptpMedicineDto, HttpSession session) {

        Long memberId = (Long) session.getAttribute("memberId");
        memberMedicineService.registerPtpMedicine(medicineId, memberId, ptpMedicineDto);

        return ResponseEntity.ok("나의 약 등록 완료");

    }

    @Operation(summary = "나의 약 전체 조회 API")
    @GetMapping("/api/v1/mypage/medicines")
    public ResponseEntity<List<MemberMedicineDto>> getMyMedicines(HttpSession session) {

        Long memberId = (Long) session.getAttribute("memberId");
        List<MemberMedicineDto> myInfo = memberMedicineService.getMyInfo(memberId);

        return new ResponseEntity<>(myInfo, HttpStatus.OK);
    }

    @Operation(summary = "위시 리스트에 알약 등록 API")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "성공적으로 등록되었습니다."),
            @ApiResponse(responseCode = "500", description = "세션이 존재하지 않습니다. 로그인 후 진행하세요")
    })
    @PostMapping("/api/v1/mypage/register/wish/{medicineId}")
    public ResponseEntity getMyWish(@PathVariable(name = "medicineId") Long medicineId, HttpSession session) {

        Long memberId = (Long) session.getAttribute("memberId");
        memberMedicineService.registerWishList(medicineId, memberId);

        return ResponseEntity.ok("위시 리스트에 등록 완료");
    }

    @Operation(summary = "위시 리스트 전체 조회 API")
    @GetMapping("/api/v1/mypage/wishlists")
    public ResponseEntity<List<WishListDto>> getWishLists(HttpSession session) {

        Long memberId = (Long) session.getAttribute("memberId");
        List<WishListDto> myWishLists = memberMedicineService.getMyWishLists(memberId);

        return new ResponseEntity<>(myWishLists, HttpStatus.OK);
    }
}
