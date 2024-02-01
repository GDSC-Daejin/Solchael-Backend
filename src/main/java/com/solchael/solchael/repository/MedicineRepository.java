package com.solchael.solchael.repository;

import com.solchael.solchael.entity.Medicine;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MedicineRepository {

    private final EntityManager em;


    public void save(Medicine medicine) {
//        System.out.println("Medicine : " + medicine.getName());
        em.persist(medicine);
    }
}
