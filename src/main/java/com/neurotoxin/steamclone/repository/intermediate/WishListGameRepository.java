package com.neurotoxin.steamclone.repository;

import com.neurotoxin.steamclone.entity.single.Game;
import com.neurotoxin.steamclone.entity.WishListGame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishListGameRepository extends JpaRepository<WishListGame, Long> {

    List<WishListGame> findWishListGameByGame(Game game);
}
