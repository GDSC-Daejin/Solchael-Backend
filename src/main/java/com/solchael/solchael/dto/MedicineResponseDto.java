package com.solchael.solchael.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MedicineResponseDto {
    private Long id;
    private String name;
    private String itemImage;;
}
