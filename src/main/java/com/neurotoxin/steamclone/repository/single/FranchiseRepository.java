package com.neurotoxin.steamclone.repository.single;

import com.neurotoxin.steamclone.entity.single.Franchise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FranchiseRepository extends JpaRepository<Franchise, Long> {
    Franchise findFranchiseByNameContaining(String name);

    Franchise findFranchiseById(long franchiseId);
}
