package com.solchael.solchael.domain.membermedicine.service;

import com.solchael.solchael.domain.membermedicine.dto.NormalMedicineDto;
import com.solchael.solchael.domain.membermedicine.dto.PtpMedicineDto;
import com.solchael.solchael.domain.membermedicine.dto.MemberMedicineDto;
import com.solchael.solchael.domain.medicine.entity.Medicine;
import com.solchael.solchael.domain.member.entity.Member;
import com.solchael.solchael.domain.membermedicine.entity.MemberMedicine;
import com.solchael.solchael.domain.medicine.repository.MedicineRepository;
import com.solchael.solchael.domain.membermedicine.repository.MemberMedicineRepository;
import com.solchael.solchael.domain.member.repository.MemberRepository;
import com.solchael.solchael.global.exception.CommonErrorCode;
import com.solchael.solchael.global.exception.RestApiException;
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
    public void registerMedicine(Long medicineId, Long memberId, NormalMedicineDto normalMedicineDto) {

        Medicine medicine = medicineRepository.findById(medicineId);
        Member member = memberRepository.findById(memberId);

        MemberMedicine memberMedicine = NormalMedicineDto.createNormalMedicine(medicine, member, normalMedicineDto);

        member.addMedicines(memberMedicine);
        medicine.addMembers(memberMedicine);

        memberMedicineRepository.save(memberMedicine);
    }

    // 사용자 약 등록 - ptp 포장 된 약
    @Transactional
    public void registerPtpMedicine(Long medicineId, Long memberId, PtpMedicineDto ptpMedicineDto) {

        Medicine medicine = medicineRepository.findById(medicineId);
        Member member = memberRepository.findById(memberId);

        MemberMedicine memberMedicine = PtpMedicineDto.createPtpMedicine(medicine, member, ptpMedicineDto);

        member.addMedicines(memberMedicine);
        medicine.addMembers(memberMedicine);

        memberMedicineRepository.save(memberMedicine);
    }

    // 내 알약 전체 조회
    public List<MemberMedicineDto> getMyInfo(Long memberId) {

        memberRepository.findById(memberId); // 세션 검사 먼저 진행

        List<MemberMedicine> memberMedicines = memberMedicineRepository.findAll(memberId); // 사용자가 등록한 약 id, 제조일자, 유통기한을 가져옴
        List<MemberMedicineDto> memberMedicine = new ArrayList<>();

        for (MemberMedicine memberMedicineDto : memberMedicines) {
            memberMedicine.add(MemberMedicineDto.toEntity(memberMedicineDto));
        }

        return memberMedicine;
    }

    // 내 알약 삭제
    @Transactional
    public MemberMedicineDto deleteMedicine(Long memberId, Long id) {

        memberRepository.findById(memberId); // 세션 검사 먼저 진행

        MemberMedicine memberMedicine = memberMedicineRepository.findById(id);

        int rows = memberMedicineRepository.delete(id, memberId);
        if (rows == 0)  throw new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND);

        return memberMedicine.toMemberMedicineDto(memberMedicine);
    }
}
