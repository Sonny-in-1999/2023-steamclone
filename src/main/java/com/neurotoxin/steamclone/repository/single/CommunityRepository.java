package com.neurotoxin.steamclone.repository.single;

import com.neurotoxin.steamclone.entity.single.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {

    Community findCommunityById(Long communityId);
}
