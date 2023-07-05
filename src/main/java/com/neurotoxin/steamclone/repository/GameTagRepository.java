package com.neurotoxin.steamclone.repository;

import com.neurotoxin.steamclone.Entity.GameTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameTagRepository extends JpaRepository<GameTag, Long> {
}
