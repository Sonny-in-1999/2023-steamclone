package com.neurotoxin.steamclone.repository;

import com.neurotoxin.steamclone.entity.single.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findCartItemById(Long cartItemId);
}
