package com.neurotoxin.steamclone.service;

import com.neurotoxin.steamclone.Entity.CartItemGame;
import com.neurotoxin.steamclone.Entity.Game;
import com.neurotoxin.steamclone.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    // 장바구니 게임 생성
    public CartItemGame create(CartItemGame cartItem) {
        cartItem.getGame().getCart().add(cartItem);
        return cartItemRepository.save(cartItem);
    }

    // 장바구니 게임 전건 조회
    public List<CartItemGame> findAllCartItems() {
        return cartItemRepository.findAll();
    }

    // 장바구니 게임 단일 조회
    public CartItemGame findCartItemById(Long cartItemId) {
        return cartItemRepository.findCartItemById(cartItemId);
    }

    // 장바구니 게임 삭제
    public void delete(CartItemGame cartItem) {
        cartItemRepository.delete(cartItem);
    }
}
