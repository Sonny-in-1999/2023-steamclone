package com.neurotoxin.steamclone.repository.single;

import com.neurotoxin.steamclone.entity.single.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, Long> {
}
