package com.solchael.solchael.domain.membermedicine.entity;

import com.solchael.solchael.domain.medicine.entity.Medicine;
import com.solchael.solchael.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
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

    private LocalDateTime startTime;
    private LocalDateTime endTime;

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