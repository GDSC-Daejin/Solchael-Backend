package com.solchael.solchael.domain.membermedicine.service;

import com.solchael.solchael.domain.medicine.dto.NormalMedicineDto;
import com.solchael.solchael.domain.medicine.dto.PtpMedicineDto;
import com.solchael.solchael.domain.membermedicine.dto.MemberMedicineDto;
import com.solchael.solchael.domain.wishlist.dto.WishListDto;
import com.solchael.solchael.domain.medicine.entity.Medicine;
import com.solchael.solchael.domain.member.entity.Member;
import com.solchael.solchael.domain.membermedicine.entity.MemberMedicine;
import com.solchael.solchael.domain.wishlist.entity.WishList;
import com.solchael.solchael.domain.medicine.repository.MedicineRepository;
import com.solchael.solchael.domain.membermedicine.repository.MemberMedicineRepository;
import com.solchael.solchael.domain.member.repository.MemberRepository;
import com.solchael.solchael.domain.wishlist.repository.WishListRepository;
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
    private final WishListRepository wishListRepository;

    // 사용자 약 등록 - ptp 포장이 아닌 약
    @Transactional
    public void registerMedicine(Long medicineId, Long memberId, NormalMedicineDto normalMedicineDto) {

        Medicine medicine = medicineRepository.findById(medicineId);
        Member member = memberRepository.findById(memberId);

        MemberMedicine memberMedicine = Medicine.createNormalMedicine(medicine, member, normalMedicineDto);

        member.addMedicines(memberMedicine);
        medicine.addMembers(memberMedicine);

        memberMedicineRepository.save(memberMedicine);
    }

    // 사용자 약 등록 - ptp 포장 된 약
    @Transactional
    public void registerPtpMedicine(Long medicineId, Long memberId, PtpMedicineDto ptpMedicineDto) {

        Medicine medicine = medicineRepository.findById(medicineId);
        Member member = memberRepository.findById(memberId);

        MemberMedicine memberMedicine = Medicine.createPtpMedicine(medicine, member, ptpMedicineDto);

        member.addMedicines(memberMedicine);
        medicine.addMembers(memberMedicine);

        memberMedicineRepository.save(memberMedicine);
    }

    // 내 알약 전체 조회
    public List<MemberMedicineDto> getMyInfo(Long id) {
        List<MemberMedicine> memberMedicines = memberMedicineRepository.findAll(id); // 사용자가 등록한 약 id, 제조일자, 유통기한을 가져옴
        List<MemberMedicineDto> memberMedicine = new ArrayList<>();

        for (MemberMedicine memberMedicineDto : memberMedicines) {
            memberMedicine.add(MemberMedicineDto.fromEntity(memberMedicineDto));
        }

        return memberMedicine;
    }

    // 위시 리스트에 알약 등록하기
    @Transactional
    public void registerWishList(Long medicineId, Long memberId) {
        Medicine medicine= medicineRepository.findById(medicineId);
        Member member = memberRepository.findById(memberId);

        WishList wishList = WishList.createWishList(medicine, member);

        medicine.addWishList(wishList);
        member.addWishList(wishList);

        wishListRepository.save(wishList);
    }

    // 위시 리스트 전체 조회
    public List<WishListDto> getMyWishLists(Long id) {
        List<WishList> lists = wishListRepository.findAll(id);
        List<WishListDto> wishLists = new ArrayList<>();

        for (WishList list : lists) {
            wishLists.add(WishListDto.fromEntity(list));
        }

        return wishLists;
    }
}
