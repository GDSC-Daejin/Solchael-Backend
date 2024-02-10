package com.solchael.solchael.entity;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class WishList {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_wish_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    // 연관 관계 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getWishLists().add(this);
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
        medicine.getWishLists().add(this);
    }

    // 생성 메서드
    public static WishList createWishList(Medicine medicine, Member member) {
        return WishList.builder()
                .member(member)
                .medicine(medicine)
                .build();
    }
}
