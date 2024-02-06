package com.solchael.solchael.repository;

import com.solchael.solchael.entity.Member;
import com.solchael.solchael.entity.MemberMedicine;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public Member findByName(String nickName) {
        List<Member> members = em.createQuery("select m from Member m where m.nickName = :nickName", Member.class)
                .setParameter("nickName", nickName)
                .getResultList();

        if (!members.isEmpty()) {
            return members.get(0);
        } else {
            return null;
        }
    }

    public Member findByEmail(String email) {
        List<Member> members = em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultList();

        if (!members.isEmpty()) {
            return members.get(0);
        } else {
            return null;
        }
    }

    public Member findById(Long id) {
        return em.createQuery("select m from Member m where m.id = :id", Member.class)
                .setParameter("id", id)
                .getSingleResult();
    }

//    // 이건 인스타 나중에 볼 게시글 저장같은 느낌
//    public MemberMedicine saveWishList(Long id) {
//
//    }
//
//    // 이건 내가 갖고 있는 약들을 등록해 놓는 것 -> 유통기한 표시
//    public MemberMedicine registerMedicine(Long id) {
//
//    }
}
