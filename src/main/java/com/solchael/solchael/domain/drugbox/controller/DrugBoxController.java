package com.solchael.solchael.domain.drugbox.controller;

import com.solchael.solchael.domain.drugbox.dto.DrugBoxResponseDto;
import com.solchael.solchael.domain.drugbox.entity.DrugBox;
import com.solchael.solchael.domain.drugbox.service.DrugBoxService;
import com.solchael.solchael.domain.drugbox.util.CsvUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Tag(name = "폐의약품 수거함 관련 컨트롤러")
public class DrugBoxController {

    private final DrugBoxService drugBoxService;

    @Operation(summary = "폐의약품 수거함 위치 조회 API")
    @GetMapping("/api/v1/drugbox")
    public ResponseEntity<List<DrugBoxResponseDto>> findAll() {
        List<DrugBoxResponseDto> response = drugBoxService.findAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


//    @Operation(summary = "csv 파일 파싱해서 DB에 저장해주는 API ( 사용하면 안 돼요 )")
//    @GetMapping("/api/v1/loadDrugBox")
//    public void saveCsvToDatabase() {
//
//        List<DrugBox> drugBoxList = loadDrugBoxList();
//        drugBoxService.saveAll(drugBoxList);
//    }
//
//    private List<DrugBox> loadDrugBoxList() {
//
//        return CsvUtils.convertToDrugBoxDtoList()
//                .stream().map(drugBoxDto ->
//                        DrugBox.builder()
//                                .id(drugBoxDto.getId())
//                                .locationName(drugBoxDto.getLocationName())
//                                .locationAddress(drugBoxDto.getLocationAddress())
////                                .latitude(drugBoxDto.getLatitude())
////                                .longitude(drugBoxDto.getLongitude())
//                                .build())
//                .collect(Collectors.toList());
//    }
}
