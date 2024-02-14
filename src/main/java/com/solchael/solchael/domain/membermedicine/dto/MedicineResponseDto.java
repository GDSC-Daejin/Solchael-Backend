package com.solchael.solchael.domain.membermedicine.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MedicineResponseDto {
    private Long id;
    private String name;
    private String itemImage;;
}
