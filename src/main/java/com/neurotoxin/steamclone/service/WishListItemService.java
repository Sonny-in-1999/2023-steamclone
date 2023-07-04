package com.neurotoxin.steamclone.service;

import com.neurotoxin.steamclone.Entity.Game;
import com.neurotoxin.steamclone.Entity.WishListGame;
import com.neurotoxin.steamclone.repository.WishListItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishListItemService {

    private final WishListItemRepository wishListItemRepository;

    // 찜 목록 게임 등록
    @Transactional
    public WishListGame create(WishListGame wishListItem) {
        wishListItem.getWishListItem().getWishList().add(wishListItem);
        return wishListItemRepository.save(wishListItem);
    }

    // 찜 목록 게임 전건 조회
    public List<WishListGame> findAllWishListItem() {
        return wishListItemRepository.findAll();
    }

    // 찜 목록 단일 게임 조회
    public WishListGame findGameById(Long wishListItemId) {
        return wishListItemRepository.findGameById(wishListItemId);
    }

    // 찜 목록 게임 삭제
    @Transactional
    public void delete(WishListGame wishListItem) {
        wishListItemRepository.delete(wishListItem);
    }

}
