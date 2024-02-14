package com.solchael.solchael.domain.member.entity;

import com.solchael.solchael.domain.member.dto.JoinRequest;
import com.solchael.solchael.domain.membermedicine.entity.MemberMedicine;
import com.solchael.solchael.domain.wishlist.entity.WishList;
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
    private String image;

    @OneToMany(mappedBy = "member")
    private List<MemberMedicine> medicines = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<WishList> wishLists = new ArrayList<>();

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

    public void addWishList(WishList wishList) {
        wishLists.add(wishList);
        wishList.setMember(this);
    }

    // 닉네임 변경
    public void changeName(String nickName) {
        this.nickName = nickName;
    }

    // 프로필 다운로드 이미지 저장
    public void uploadProfileImage(Member member, String imageUrl) {
        this.image = imageUrl;
    }
}
