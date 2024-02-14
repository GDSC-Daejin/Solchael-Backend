package com.solchael.solchael.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PtpMedicineDto {
    private LocalDateTime expiration; // 유통기한
}
