package com.neurotoxin.steamclone.repository;

import com.neurotoxin.steamclone.Entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    Game findGameById(Long gameId);

    List<Game> findGameByName(String gameName);
}
