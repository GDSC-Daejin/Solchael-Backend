package com.solchael.solchael.service;

import com.solchael.solchael.dto.WishListDto;
import com.solchael.solchael.entity.Medicine;
import com.solchael.solchael.entity.Member;
import com.solchael.solchael.entity.WishList;
import com.solchael.solchael.repository.MedicineRepository;
import com.solchael.solchael.repository.MemberRepository;
import com.solchael.solchael.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishListService {

    private final WishListRepository wishListRepository;
    private final MemberRepository memberRepository;
    private final MedicineRepository medicineRepository;

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
