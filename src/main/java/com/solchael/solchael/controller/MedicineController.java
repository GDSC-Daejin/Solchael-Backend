package com.solchael.solchael.controller;

import com.solchael.solchael.dto.MedicineDto;
import com.solchael.solchael.dto.MedicineResponseDto;
import com.solchael.solchael.service.MedicineService;
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

    @GetMapping("/api/v1/search")
    public ResponseEntity<List<MedicineResponseDto>> searchMedicine(@RequestParam String name) {
        List<MedicineResponseDto> medicine = medicineService.searchMedicine(name);
        return new ResponseEntity<>(medicine, HttpStatus.OK);
    }

    @GetMapping("/api/v1/medicine/{medicineId}")
    public ResponseEntity<MedicineDto> getMedicine(@PathVariable Long medicineId) {
        MedicineDto medicine = medicineService.findById(medicineId);
        return new ResponseEntity<>(medicine, HttpStatus.OK);
    }

    @GetMapping("/api/v1/recommend")
    public ResponseEntity<List<MedicineResponseDto>> getRecommend(@RequestParam String symptom) {
        List<MedicineResponseDto> recommend = medicineService.recommendMedicine(symptom);
        return new ResponseEntity<>(recommend, HttpStatus.OK);
    }
}
