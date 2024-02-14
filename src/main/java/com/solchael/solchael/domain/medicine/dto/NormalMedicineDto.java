package com.solchael.solchael.domain.medicine.dto;

import com.solchael.solchael.domain.member.dto.MedicineType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NormalMedicineDto {

    private MedicineType type;
    private LocalDateTime startTime;
}
