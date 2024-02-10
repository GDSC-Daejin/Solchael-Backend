package com.solchael.solchael.repository;

import com.solchael.solchael.entity.WishList;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
