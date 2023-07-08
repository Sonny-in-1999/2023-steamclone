package com.neurotoxin.steamclone.repository;

import com.neurotoxin.steamclone.entity.single.Game;
import com.neurotoxin.steamclone.entity.GameTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameTagRepository extends JpaRepository<GameTag, Long> {

    List<GameTag> findGameTagByGame(Game game);
}
