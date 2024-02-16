package com.solchael.solchael.domain.medicine.controller;

import com.solchael.solchael.domain.medicine.dto.MedicineDto;
import com.solchael.solchael.domain.membermedicine.dto.MedicineResponseDto;
import com.solchael.solchael.domain.medicine.service.MedicineService;
import com.solchael.solchael.domain.rank.dto.SearchRankDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "약 관련 컨트롤러")
@RestController
@RequiredArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;

    @Operation(summary = "약 검색 API", description = "약 이름을 입력해주세요")
    @GetMapping("/api/v1/search")
    public ResponseEntity<List<MedicineResponseDto>> searchMedicine(@RequestParam(name = "name") String name) {
        List<MedicineResponseDto> medicine = medicineService.searchMedicine(name);
        return new ResponseEntity<>(medicine, HttpStatus.OK);
    }

    @Operation(summary = "약 상세조회 API")
    @GetMapping("/api/v1/medicine/{medicineId}")
    public ResponseEntity<MedicineDto> getMedicine(@PathVariable(name = "medicineId") Long medicineId) {
        MedicineDto medicine = medicineService.findById(medicineId);
        return new ResponseEntity<>(medicine, HttpStatus.OK);
    }

    @Operation(summary = "편의점 약 추천 API", description = "증상을 입력해주세요")
    @GetMapping("/api/v1/recommend")
    public ResponseEntity<List<MedicineResponseDto>> getRecommend(@RequestParam(name = "symptom") String symptom) {
        List<MedicineResponseDto> recommend = medicineService.recommendMedicine(symptom);
        return new ResponseEntity<>(recommend, HttpStatus.OK);
    }

    @Operation(summary = "약 실시간 검색 순위")
    @GetMapping("/api/v1/rank")
    public ResponseEntity<List<SearchRankDto>> findAllRank() {
        List<SearchRankDto> list = medicineService.findRank();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
