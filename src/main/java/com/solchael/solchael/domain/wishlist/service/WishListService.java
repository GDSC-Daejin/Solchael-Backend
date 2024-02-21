package com.solchael.solchael.domain.wishlist.service;

import com.solchael.solchael.domain.wishlist.dto.WishListDto;
import com.solchael.solchael.domain.medicine.entity.Medicine;
import com.solchael.solchael.domain.member.entity.Member;
import com.solchael.solchael.domain.wishlist.entity.WishList;
import com.solchael.solchael.global.exception.CommonErrorCode;
import com.solchael.solchael.global.exception.RestApiException;
import com.solchael.solchael.domain.medicine.repository.MedicineRepository;
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
            wishLists.add(WishListDto.toEntity(list));
        }

        return wishLists;
    }

    // 위시 리스트에서 삭제
    @Transactional
    public WishListDto deleteMyWish(Long id, Long memberId) {

        memberRepository.findById(memberId); // 세션 검사 먼저

        WishList wishList = wishListRepository.findById(id);
        int rows = wishListRepository.delete(id, memberId);
        if (rows == 0)  throw new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND);

//        WishList wish = WishList.createWishList(medicineRepository.findById(medicineId), memberRepository.findById(memberId));
//        return WishListDto.toEntity(wish);

        return wishList.toWishListDto(wishList);
    }
}
