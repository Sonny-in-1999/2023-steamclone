package com.neurotoxin.steamclone.repository.connect;

import com.neurotoxin.steamclone.entity.single.Game;
import com.neurotoxin.steamclone.entity.connect.WishListGame;
import com.neurotoxin.steamclone.entity.single.WishListItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishListGameRepository extends JpaRepository<WishListGame, Long> {

    List<WishListGame> findWishListGameByGame(Game game);

    WishListGame findWishListGameByWishListItem(WishListItem wishListItem);
}
