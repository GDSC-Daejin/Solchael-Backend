package com.solchael.solchael.entity;

import com.solchael.solchael.dto.JoinRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String nickName;
    private String email;
    private String password;

    @OneToMany(mappedBy = "member")
    private List<MemberMedicine> medicines = new ArrayList<>();

    // 생성 메서드
    public static Member createMember(JoinRequest joinRequest) {
        Member member = new Member();
        member.nickName = joinRequest.getNickName();
        member.email = joinRequest.getEmail();
        member.password = joinRequest.getPassword();

        return member;
    }

    // 연관 관계 메서드
    public void addMedicines(MemberMedicine medicine) {
        medicines.add(medicine);
        medicine.setMember(this);
    }

    // 닉네임 변경
    public void changeName(String nickName) {
        this.nickName = nickName;
    }
}
