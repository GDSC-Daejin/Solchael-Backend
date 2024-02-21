package com.solchael.solchael.domain.medicine.dto;

import com.solchael.solchael.domain.medicine.entity.Medicine;
import com.solchael.solchael.domain.member.dto.MedicineType;
import com.solchael.solchael.domain.member.entity.Member;
import com.solchael.solchael.domain.membermedicine.entity.MemberMedicine;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NormalMedicineDto {

    private MedicineType type;
    private LocalDateTime startTime;

    public static MemberMedicine createNormalMedicine(Medicine medicine, Member member, NormalMedicineDto medicineDto) {

        return MemberMedicine.builder()
                .member(member)
                .medicine(medicine)
                .startTime(medicineDto.getStartTime())
                .endTime(setExpiration(medicineDto.getType(), medicineDto.getStartTime()))
                .build();
    }

    /**
     * 조제약은 1년/ PTP 는 표시된 유통기한대로/
     * 가루약은 6개월 / 연고, 크림 6개월
     * 시럽제는 1개월 / 점비제, 점이제, 가글 약은 1개월/
     */
    // 유통기한 설정
    public static LocalDateTime setExpiration(MedicineType medicineType, LocalDateTime startTime) {
        // 타입별로 유통기한 설정
        if (medicineType == MedicineType.PILL) {
            return startTime.plusYears(1);
        } else if (medicineType == MedicineType.POWDER || medicineType ==  MedicineType.OINTMENT) {
            return  startTime.plusMonths(6);
        } else {
            return startTime.plusMonths(1);
        }
    }
}
