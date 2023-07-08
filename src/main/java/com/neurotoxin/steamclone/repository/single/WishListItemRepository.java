package com.neurotoxin.steamclone.repository.single;

import com.neurotoxin.steamclone.entity.single.WishListItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListItemRepository extends JpaRepository<WishListItem, Long> {

    WishListItem findGameById(Long wishListItemId);
}
