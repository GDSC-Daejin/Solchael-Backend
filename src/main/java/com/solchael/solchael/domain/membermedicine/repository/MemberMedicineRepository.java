package com.solchael.solchael.domain.membermedicine.repository;

import com.solchael.solchael.domain.member.repository.MemberRepository;
import com.solchael.solchael.domain.membermedicine.entity.MemberMedicine;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberMedicineRepository {

    private final EntityManager em;

    public void save(MemberMedicine memberMedicine) {
        em.persist(memberMedicine);
    }

    public MemberMedicine findById(Long id) {
        return em.createQuery("select m from MemberMedicine m where m.id = :id", MemberMedicine.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public List<MemberMedicine> findAll(Long memberId) {
        return em.createQuery("select m from MemberMedicine m where m.member.id = :id", MemberMedicine.class)
                .setParameter("id", memberId)
                .getResultList();
    }

    public int delete(Long id, Long memberId) {
        return em.createQuery("delete from MemberMedicine m where m.id = :id and m.member.id = :memberId")
                .setParameter("id", id)
                .setParameter("memberId", memberId)
                .executeUpdate();
    }
}
