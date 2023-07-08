package com.neurotoxin.steamclone.service.connect;


import com.neurotoxin.steamclone.entity.single.Game;
import com.neurotoxin.steamclone.entity.connect.WishListGame;
import com.neurotoxin.steamclone.entity.single.WishListItem;
import com.neurotoxin.steamclone.repository.connect.WishListGameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishListGameService {

    private final WishListGameRepository wishListGameRepository;

    // Connect Tables
    @Transactional
    public WishListGame create(Game game, WishListItem wishListItem) {
        WishListGame wishListGame = new WishListGame(game, wishListItem);
        return wishListGameRepository.save(wishListGame);
    }

    // Searching Single WishListGame with Single wishListItemId => Convert to Game
    public Game findGameByItemId(WishListItem wishListItem) {
        WishListGame findWishListGame = wishListGameRepository.findWishListGameByWishListItem(wishListItem);
        return findWishListGame.getGame();
    }

    // Searching All WishListGame with Single Game
    public List<WishListGame> getWishListGames(Game game) {
        return wishListGameRepository.findWishListGameByGame(game);
    }

    // Delete Single Table
    @Transactional
    public void delete(WishListGame wishListGame) {
        wishListGameRepository.delete(wishListGame);
    }

    // Delete Connected All Table with Single Game
    @Transactional
    public void disconnect(Game game) {
        List<WishListGame> wishListGames = getWishListGames(game);
        for (WishListGame wishListGame : wishListGames) {
            delete(wishListGame);
        }
    }

}
