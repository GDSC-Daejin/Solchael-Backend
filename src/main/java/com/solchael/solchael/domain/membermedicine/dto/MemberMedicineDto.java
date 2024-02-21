package com.solchael.solchael.domain.membermedicine.dto;

import com.solchael.solchael.domain.membermedicine.entity.MemberMedicine;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MemberMedicineDto {

    private Long id; // memberMedicine PK
    private Long medicineId; // 약 id
    private String name;
    private String image;
    private LocalDateTime startTime;
    private LocalDateTime expiration;

    public static MemberMedicineDto toEntity(MemberMedicine memberMedicine) {

        return MemberMedicineDto.builder()
                .id(memberMedicine.getId())
                .medicineId(memberMedicine.getMedicine().getId())
                .name(memberMedicine.getMedicine().getName())
                .image(memberMedicine.getMedicine().getItemImage())
                .startTime(memberMedicine.getStartTime())
                .expiration(memberMedicine.getEndTime())
                .build();

    }
}
