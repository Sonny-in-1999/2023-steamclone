package com.neurotoxin.steamclone.repository;

import com.neurotoxin.steamclone.Entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
