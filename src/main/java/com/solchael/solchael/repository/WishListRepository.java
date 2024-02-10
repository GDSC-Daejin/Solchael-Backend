package com.solchael.solchael.repository;

import com.solchael.solchael.entity.WishList;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WishListRepository {

    private final EntityManager em;

    public void save(WishList wishList) {
        em.persist(wishList);
    }

    public List<WishList> findAll(Long id) {
        return em.createQuery("select m from WishList m where m.member.id = :id", WishList.class)
                .setParameter("id", id)
                .getResultList();
    }

    public void delete(Long medicineId, Long memberId) {
        return Optional.of(em.createQuery("delete from WishList m where m.medicine.id = :medicineId and m.member.id = :memberId")
                .setParameter("medicineId", medicineId)
                .setParameter("memberId", memberId)
                .executeUpdate());
    }
}
