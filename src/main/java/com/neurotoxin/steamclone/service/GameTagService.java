package com.neurotoxin.steamclone.service;

import com.neurotoxin.steamclone.Entity.GameTag;
import com.neurotoxin.steamclone.repository.GameTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameTagService {

    private final GameTagRepository gameTagRepository;

    // Connect Tables
    public GameTag create(GameTag gameTag) {
        return gameTagRepository.save(gameTag);
    }

    // Disconnect Table
    public void delete(GameTag gameTag) {
        gameTagRepository.delete(gameTag);
    }
}
