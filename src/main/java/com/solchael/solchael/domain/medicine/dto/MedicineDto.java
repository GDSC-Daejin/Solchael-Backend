package com.solchael.solchael.domain.medicine.dto;

import com.solchael.solchael.domain.medicine.entity.Medicine;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MedicineDto {

    private String name; // 약 이름
    private String useMethodQesitm; // 사용법
    private String efcyQesitm; // 효능
    private String atpnQesitm; // 주의사항
    private String seQesitm; // 부작용
    private String itemImage; // 이미지

    public static MedicineDto createMedicineDto(String itemName, String useMethodQesitm, String efcyQesitm, String atpnQesitm, String seQesitm, String itemImage) {
        return MedicineDto.builder()
                .name(itemName)
                .useMethodQesitm(useMethodQesitm)
                .efcyQesitm(efcyQesitm)
                .atpnQesitm(atpnQesitm)
                .seQesitm(seQesitm)
                .itemImage(itemImage)
                .build();
    }

    // DTO -> Entity
    public Medicine toEntity(MedicineDto medicineDto) {

        return Medicine.builder()
                .name(medicineDto.getName()).useMethodQesitm(medicineDto.getUseMethodQesitm())
                .efcyQesitm(medicineDto.getEfcyQesitm()).atpnQesitm(medicineDto.getAtpnQesitm())
                .seQesitm(medicineDto.getSeQesitm())
                .itemImage(medicineDto.getItemImage()).build();
    }
}
