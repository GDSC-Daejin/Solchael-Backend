package com.solchael.solchael.entity;

import jakarta.persistence.*;
import lombok.*;

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

    // 연관관계 메서드
    public void addMembers(MemberMedicine member) {
        members.add(member);
        member.setMedicine(this);
    }
}
