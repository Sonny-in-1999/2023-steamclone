package com.neurotoxin.steamclone.service;


import com.neurotoxin.steamclone.entity.single.Game;
import com.neurotoxin.steamclone.entity.WishListGame;
import com.neurotoxin.steamclone.entity.single.WishListItem;
import com.neurotoxin.steamclone.repository.WishListGameRepository;
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
