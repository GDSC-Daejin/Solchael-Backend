package com.solchael.solchael.service;

import com.solchael.solchael.dto.MemberMedicineDto;
import com.solchael.solchael.dto.PtpMedicineDto;
import com.solchael.solchael.dto.NormalMedicineDto;
import com.solchael.solchael.entity.Medicine;
import com.solchael.solchael.entity.Member;
import com.solchael.solchael.entity.MemberMedicine;
import com.solchael.solchael.repository.MedicineRepository;
import com.solchael.solchael.repository.MemberMedicineRepository;
import com.solchael.solchael.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberMedicineService {

    private final MemberMedicineRepository memberMedicineRepository;
    private final MedicineRepository medicineRepository;
    private final MemberRepository memberRepository;

    // 사용자 약 등록 - ptp 포장이 아닌 약
    @Transactional
    public MemberMedicineDto registerMedicine(Long medicineId, Long memberId, NormalMedicineDto normalMedicineDto) {

        Medicine medicine = medicineRepository.findById(medicineId);
        Member member = memberRepository.findById(memberId);

        MemberMedicine memberMedicine = NormalMedicineDto.createNormalMedicine(medicine, member, normalMedicineDto);

        member.addMedicines(memberMedicine);
        medicine.addMembers(memberMedicine);

        memberMedicineRepository.save(memberMedicine);

        return MemberMedicineDto.fromEntity(memberMedicine);
    }

    // 사용자 약 등록 - ptp 포장 된 약
    @Transactional
    public MemberMedicineDto registerPtpMedicine(Long medicineId, Long memberId, PtpMedicineDto ptpMedicineDto) {

        Medicine medicine = medicineRepository.findById(medicineId);
        Member member = memberRepository.findById(memberId);

        MemberMedicine memberMedicine = PtpMedicineDto.createPtpMedicine(medicine, member, ptpMedicineDto);

        member.addMedicines(memberMedicine);
        medicine.addMembers(memberMedicine);

        memberMedicineRepository.save(memberMedicine);

        return MemberMedicineDto.fromEntity(memberMedicine);
    }

    // 내 정보 가져오기
    public List<MemberMedicineDto> getMyInfo(Long id) {
        List<MemberMedicine> memberMedicines = memberMedicineRepository.findAll(id); // 사용자가 등록한 약 id, 제조일자, 유통기한을 가져옴
        List<MemberMedicineDto> memberMedicine = new ArrayList<>();

        for (MemberMedicine memberMedicineDto : memberMedicines) {
            memberMedicine.add(MemberMedicineDto.fromEntity(memberMedicineDto));
        }

        return memberMedicine;
    }

}
