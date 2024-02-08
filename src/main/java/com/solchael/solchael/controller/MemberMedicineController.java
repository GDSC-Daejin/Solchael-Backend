package com.solchael.solchael.controller;

import com.solchael.solchael.dto.MemberMedicineDto;
import com.solchael.solchael.dto.PtpMedicineDto;
import com.solchael.solchael.dto.NormalMedicineDto;
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
    public ResponseEntity<MemberMedicineDto> addMedicine(@PathVariable("medicineId") Long medicineId, @RequestBody NormalMedicineDto normalMedicineDto, HttpSession session) {

        Long memberId = (Long) session.getAttribute("memberId");
        MemberMedicineDto response = memberMedicineService.registerMedicine(medicineId, memberId, normalMedicineDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    // ptp 포장이니까 유통기한 입력 o -> 제조일자 입력 x
    @Operation(summary = "PTP 포장 약 등록 API")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "성공적으로 등록되었습니다."),
            @ApiResponse(responseCode = "500", description = "세션이 존재하지 않습니다. 로그인 후 진행하세요")
    })
    @PostMapping("/api/v1/mypage/register/ptp/{medicineId}")
    public ResponseEntity<MemberMedicineDto> addPtpMedicine(@PathVariable("medicineId") Long medicineId, @RequestBody PtpMedicineDto ptpMedicineDto, HttpSession session) {

        Long memberId = (Long) session.getAttribute("memberId");
        MemberMedicineDto response = memberMedicineService.registerPtpMedicine(medicineId, memberId, ptpMedicineDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @Operation(summary = "나의 약 전체 조회 API")
    @GetMapping("/api/v1/mypage/medicines/")
    public ResponseEntity<List<MemberMedicineDto>> getMyMedicines(HttpSession session) {

        Long memberId = (Long) session.getAttribute("memberId");
        List<MemberMedicineDto> myInfo = memberMedicineService.getMyInfo(memberId);

        return new ResponseEntity<>(myInfo, HttpStatus.OK);
    }
}
