package com.neurotoxin.steamclone.repository;

import com.neurotoxin.steamclone.entity.WishListGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListItemRepository extends JpaRepository<WishListGame, Long> {

    WishListGame findGameById(Long wishListItemId);
}
