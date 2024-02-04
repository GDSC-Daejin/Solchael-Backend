package com.solchael.solchael.repository;

import com.solchael.solchael.entity.Medicine;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MedicineRepository {

    private final EntityManager em;


    public void save(Medicine medicine) {
//        System.out.println("Medicine : " + medicine.getName());
        em.persist(medicine);
    }

    public List<Medicine> findByName(String name) {
        List<Medicine> medicines = em.createQuery("select m from Medicine m where m.name like concat('%', :name, '%')", Medicine.class)
                .setParameter("name", name)
                .getResultList();

        if (medicines.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 약이 존재하지 않습니다.");
        } else {
            return medicines;
        }
    }

    // 증상으로 약 검색 ( 추천 약 )
    public List<Medicine> findBySymptom(String symptom) {
        List<Medicine> medicines = em.createQuery("select m from Medicine m where m.efcyQesitm like concat('%', :efcyQesitm, '%') and m.store = true", Medicine.class)
                .setParameter("efcyQesitm", symptom)
                .getResultList();

        if (medicines.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 약이 존재하지 않습니다.");
        } else {
            return medicines;
        }
    }

    public Medicine findById(Long id) {
        return em.createQuery("select m from Medicine m where m.id = :id", Medicine.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
