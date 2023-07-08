package com.neurotoxin.steamclone.service.connect;


import com.neurotoxin.steamclone.entity.single.CartItem;
import com.neurotoxin.steamclone.entity.connect.CartItemGame;
import com.neurotoxin.steamclone.entity.single.Game;
import com.neurotoxin.steamclone.repository.connect.CartItemGameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartItemGameService {

    private final CartItemGameRepository cartItemGameRepository;

    // 장바구니 게임 등록 (장바구니 게임 - 멤버 연동)
    @Transactional
    public CartItemGame create(Game game, CartItem cartItem) {
        CartItemGame cartItemGame = new CartItemGame(game, cartItem);
        return cartItemGameRepository.save(cartItemGame);
    }

    // Searching All CartItemGame with Single Game
    public List<CartItemGame> getCartItemGame(Game game) {
        return cartItemGameRepository.findCartItemGameByGame(game);
    }

    // Delete Single Table
    @Transactional
    public void delete(CartItemGame cartItemGame) {
        cartItemGameRepository.delete(cartItemGame);
    }

    // Delete Connected All Table with Single Game
    @Transactional
    public void disconnect(Game game) {
        List<CartItemGame> cartItemGames = getCartItemGame(game);
        for (CartItemGame cartItemGame : cartItemGames) {
            delete(cartItemGame);
        }
    }
}
