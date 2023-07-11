package com.neurotoxin.steamclone.repository.single;

import com.neurotoxin.steamclone.entity.single.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
