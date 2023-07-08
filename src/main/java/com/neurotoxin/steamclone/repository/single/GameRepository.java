package com.neurotoxin.steamclone.repository;

import com.neurotoxin.steamclone.entity.single.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
    Game findGameById(Long gameId);

    Game findGameByName(String gameName);

}
