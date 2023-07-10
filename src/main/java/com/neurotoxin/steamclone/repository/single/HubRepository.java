package com.neurotoxin.steamclone.repository.single;

import com.neurotoxin.steamclone.entity.single.Hub;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubRepository extends JpaRepository<Hub, Long> {
    Hub findHubById(Long hubId);
}
