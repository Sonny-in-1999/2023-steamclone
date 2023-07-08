package com.neurotoxin.steamclone.repository;

import com.neurotoxin.steamclone.entity.CartItemGame;
import com.neurotoxin.steamclone.entity.single.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemGameRepository extends JpaRepository<CartItemGame, Long> {

    List<CartItemGame> findCartItemGameByGame(Game game);
}
