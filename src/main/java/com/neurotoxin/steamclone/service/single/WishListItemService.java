package com.neurotoxin.steamclone.service.single;

import com.neurotoxin.steamclone.entity.single.Member;
import com.neurotoxin.steamclone.entity.single.WishListItem;
import com.neurotoxin.steamclone.repository.single.MemberRepository;
import com.neurotoxin.steamclone.repository.single.WishListItemRepository;
import com.neurotoxin.steamclone.service.connect.CartItemGameService;
import com.neurotoxin.steamclone.service.connect.WishListGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishListItemService {

    private final MemberRepository memberRepository;
    private final WishListItemRepository wishListItemRepository;

    // 찜 목록 게임 등록 (찜 목록 게임 - 멤버 연동)
    @Transactional
    public void create(Member member, WishListItem wishListItem) {
        Member findMember = memberRepository.findMemberById(member.getId());
        findMember.getWishList().add(wishListItem);
        wishListItem.setMember(findMember);
    }

    // 찜 목록 게임 전건 조회
    public List<WishListItem> findAllWishListItem() {
        return wishListItemRepository.findAll();
    }

    // 찜 목록 단일 게임 조회
    public WishListItem findGameById(Long wishListItemId) {
        return wishListItemRepository.findGameById(wishListItemId);
    }

    // 찜 목록 게임 삭제
    @Transactional
    public void delete(WishListItem wishListItem) {
        wishListItemRepository.delete(wishListItem);
    }
}
