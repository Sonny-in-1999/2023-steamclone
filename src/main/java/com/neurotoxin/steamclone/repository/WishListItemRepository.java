package com.neurotoxin.steamclone.repository;

import com.neurotoxin.steamclone.Entity.Game;
import com.neurotoxin.steamclone.Entity.WishListGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListItemRepository extends JpaRepository<WishListGame, Long> {

    WishListGame findGameById(Long wishListItemId);
}
