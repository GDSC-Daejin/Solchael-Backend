package com.solchael.solchael.domain.membermedicine.controller;

import com.solchael.solchael.domain.medicine.dto.MedicineDto;
import com.solchael.solchael.domain.medicine.dto.MedicineResponseDto;
import com.solchael.solchael.domain.membermedicine.dto.NormalMedicineDto;
import com.solchael.solchael.domain.membermedicine.dto.PtpMedicineDto;
import com.solchael.solchael.domain.membermedicine.dto.MemberMedicineDto;
import com.solchael.solchael.domain.membermedicine.service.MemberMedicineService;
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
@Tag(name = "나의 약 관련 컨트롤러")
public class MemberMedicineController {

    private final MemberMedicineService memberMedicineService;

    // ptp 포장이 아니니까 유통기한 입력 x -> 제조일자를 입력
    @Operation(summary = "일반 약 등록 API")
    @PostMapping("/api/v1/mypage/{medicineId}")
    public ResponseEntity addMedicine(@PathVariable("medicineId") Long medicineId, @RequestBody NormalMedicineDto normalMedicineDto, HttpSession session) {

        Long memberId = (Long) session.getAttribute("memberId");
        memberMedicineService.registerMedicine(medicineId, memberId, normalMedicineDto);

        return ResponseEntity.ok("나의 약 등록 완료");

    }

    // ptp 포장이니까 유통기한 입력 o -> 제조일자 입력 x
    @Operation(summary = "PTP 포장 약 등록 API")
    @PostMapping("/api/v1/mypage/ptp/{medicineId}")
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

    @Operation(summary = "나의 약 삭제 API")
    @DeleteMapping("/api/v1/mypage/medicines/{id}")
    public ResponseEntity<MemberMedicineDto> deleteMyMedicine(@PathVariable(name = "id") Long id, HttpSession session) {

        Long memberId = (Long) session.getAttribute("memberId");
        MemberMedicineDto memberMedicineDto = memberMedicineService.deleteMedicine(memberId, id);

        return new ResponseEntity<>(memberMedicineDto, HttpStatus.OK);
    }
}
