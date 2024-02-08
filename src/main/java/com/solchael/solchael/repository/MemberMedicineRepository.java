package com.solchael.solchael.repository;

import com.solchael.solchael.entity.MemberMedicine;
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

    public List<MemberMedicine> findAll(Long id) {
        return em.createQuery("select m from MemberMedicine m where m.member.id = :id", MemberMedicine.class)
                .setParameter("id", id)
                .getResultList();
    }
}
