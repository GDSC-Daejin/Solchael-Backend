package com.solchael.solchael.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NormalMedicineDto {

    private MedicineType type;
    private LocalDateTime startTime;
}
