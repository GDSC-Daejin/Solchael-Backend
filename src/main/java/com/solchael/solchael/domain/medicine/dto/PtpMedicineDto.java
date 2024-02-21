package com.solchael.solchael.domain.medicine.dto;

import com.solchael.solchael.domain.medicine.entity.Medicine;
import com.solchael.solchael.domain.member.entity.Member;
import com.solchael.solchael.domain.membermedicine.entity.MemberMedicine;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PtpMedicineDto {
    private LocalDateTime expiration; // 유통기한

    public static MemberMedicine createPtpMedicine(Medicine medicine, Member member, PtpMedicineDto ptpMedicineDto) {
        return MemberMedicine.builder()
                .member(member)
                .medicine(medicine)
                .endTime(ptpMedicineDto.getExpiration())
                .build();
    }
}
