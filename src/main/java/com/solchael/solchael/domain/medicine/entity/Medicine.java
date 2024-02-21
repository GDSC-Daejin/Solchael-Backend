package com.solchael.solchael.domain.medicine.entity;

import com.solchael.solchael.domain.medicine.dto.MedicineDto;
import com.solchael.solchael.domain.medicine.dto.NormalMedicineDto;
import com.solchael.solchael.domain.medicine.dto.PtpMedicineDto;
import com.solchael.solchael.domain.member.dto.MedicineType;
import com.solchael.solchael.domain.membermedicine.dto.MedicineResponseDto;
import com.solchael.solchael.domain.member.entity.Member;
import com.solchael.solchael.domain.membermedicine.entity.MemberMedicine;
import com.solchael.solchael.domain.wishlist.entity.WishList;
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

    public static Medicine createMedicine(String itemName, String useMethodQesitm, String efcyQesitm, String atpnQesitm, String seQesitm, String itemImage) {
        return Medicine.builder()
                .name(itemName)
                .useMethodQesitm(useMethodQesitm)
                .efcyQesitm(efcyQesitm)
                .atpnQesitm(atpnQesitm)
                .seQesitm(seQesitm)
                .itemImage(itemImage)
                .build();
    }

    public static MedicineResponseDto searchMedicine(Long id, String itemName, String itemImage) {
        return MedicineResponseDto.builder()
                .id(id)
                .name(itemName)
                .itemImage(itemImage)
                .build();
    }
}
