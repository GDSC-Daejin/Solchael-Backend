package com.solchael.solchael.entity;

import com.solchael.solchael.dto.MedicineResponseDto;
import com.solchael.solchael.dto.MedicineType;
import com.solchael.solchael.dto.NormalMedicineDto;
import com.solchael.solchael.dto.PtpMedicineDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Medicine {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medicine_id")
    private Long id; // 약 식별자

    @OneToMany(mappedBy = "medicine")
    private List<MemberMedicine> members = new ArrayList<>();

    @OneToMany(mappedBy = "medicine")
    private List<WishList> wishLists = new ArrayList<>();

    @Column(columnDefinition = "VARCHAR(4000)")
    private String name; // 약 이름

    @Column(columnDefinition = "text")
    private String useMethodQesitm; // 사용법

    @Column(columnDefinition = "text")
    private String efcyQesitm; // 효능

    @Column(columnDefinition = "text")
    private String atpnQesitm; // 주의사항

    @Column(columnDefinition = "text")
    private String seQesitm; // 부작용

    @Column(columnDefinition = "VARCHAR(300)")
    private String itemImage; // 이미지

    private boolean store; // 편의점 판매 여부

    // 연관관계 메서드
    public void addMembers(MemberMedicine member) {
        members.add(member);
        member.setMedicine(this);
    }

    public  void addWishList(WishList wishList) {
        wishLists.add(wishList);
        wishList.setMedicine(this);
    }

    public static MemberMedicine createNormalMedicine(Medicine medicine, Member member, NormalMedicineDto medicineDto) {

        return MemberMedicine.builder()
                .member(member)
                .medicine(medicine)
                .startTime(medicineDto.getStartTime())
                .endTime(setExpiration(medicineDto.getType(), medicineDto.getStartTime()))
                .build();
    }

    public static MemberMedicine createPtpMedicine(Medicine medicine, Member member, PtpMedicineDto ptpMedicineDto) {
        return MemberMedicine.builder()
                .member(member)
                .medicine(medicine)
                .endTime(ptpMedicineDto.getExpiration())
                .build();
    }

    public static MedicineResponseDto searchMedicine(Long id, String itemName, String itemImage) {
        return MedicineResponseDto.builder()
                .id(id)
                .name(itemName)
                .itemImage(itemImage)
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
