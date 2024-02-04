package com.solchael.solchael.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MedicineResponseDto {
    private Long id;
    private String name;
    private String itemImage;;

    public static MedicineResponseDto searchMedicine(Long id, String itemName, String itemImage) {
        MedicineResponseDto medicineDto = new MedicineResponseDto();
        medicineDto.setId(id);
        medicineDto.setName(itemName);
        medicineDto.setItemImage(itemImage);

        return medicineDto;
    }
}
