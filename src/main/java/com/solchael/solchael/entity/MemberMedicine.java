package com.solchael.solchael.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberMedicine {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_medicine_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 연관관계 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getMedicines().add(this);
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
        medicine.getMembers().add(this);
    }
}