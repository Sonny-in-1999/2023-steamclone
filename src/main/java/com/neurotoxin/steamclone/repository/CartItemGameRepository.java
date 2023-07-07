package com.neurotoxin.steamclone.repository;

import com.neurotoxin.steamclone.Entity.CartItemGame;
import com.neurotoxin.steamclone.Entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemGameRepository extends JpaRepository<CartItemGame, Long> {

    List<CartItemGame> findCartItemGameByGame(Game game);
}
