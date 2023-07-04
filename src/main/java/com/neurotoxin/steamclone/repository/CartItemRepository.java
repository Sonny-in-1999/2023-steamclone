package com.neurotoxin.steamclone.repository;

import com.neurotoxin.steamclone.Entity.CartItemGame;
import com.neurotoxin.steamclone.Entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItemGame, Long> {
    CartItemGame findCartItemById(Long cartItemId);
}
