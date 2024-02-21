package com.solchael.solchael.domain.medicine.service;

import com.solchael.solchael.domain.rank.service.SearchRankService;
import com.solchael.solchael.domain.medicine.dto.MedicineDto;
import com.solchael.solchael.domain.membermedicine.dto.MedicineResponseDto;
import com.solchael.solchael.domain.medicine.entity.Medicine;
import com.solchael.solchael.domain.medicine.repository.MedicineRepository;
import com.solchael.solchael.domain.rank.dto.SearchRankDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicineService {

    private final MedicineRepository medicineRepository;
    private final SearchRankService searchRankService;

    public List<MedicineResponseDto> searchMedicine(String name) {
        List<Medicine> medicineList = medicineRepository.findByName(name);
        List<MedicineResponseDto> medicines = new ArrayList<>();
        for (Medicine medicine : medicineList) {
            medicines.add(Medicine.searchMedicine(medicine.getId(), medicine.getName(), medicine.getItemImage()));
        }
        return medicines;
    }

    public List<MedicineResponseDto> recommendMedicine(String symptom) {
        List<Medicine> medicineList = medicineRepository.findBySymptom(symptom);
        List<MedicineResponseDto> recommend = new ArrayList<>();
        for (Medicine medicine : medicineList) {
            recommend.add(Medicine.searchMedicine(medicine.getId(), medicine.getName(), medicine.getItemImage()));
        }
        return recommend;
    }

    public MedicineDto findById(Long id) {
        Medicine medicine = medicineRepository.findById(id);

        // redis
        searchRankService.save(SearchRankDto.createRecentSearchDto(medicine));

        return MedicineDto.createMedicineDto(medicine.getName(), medicine.getUseMethodQesitm(),
                medicine.getEfcyQesitm(), medicine.getAtpnQesitm(), medicine.getSeQesitm(), medicine.getItemImage());
    }

    public List<SearchRankDto> findRank() {
        return searchRankService.getAllRank();
    }
}
