package com.neurotoxin.steamclone.repository;

import com.neurotoxin.steamclone.Entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Game, Long> {
    Game findPurchasedItemById(Long purchasedGame);
}
