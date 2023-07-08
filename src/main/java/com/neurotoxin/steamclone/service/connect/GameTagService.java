package com.neurotoxin.steamclone.service.connect;

import com.neurotoxin.steamclone.entity.single.Game;
import com.neurotoxin.steamclone.entity.connect.GameTag;
import com.neurotoxin.steamclone.entity.single.Tag;
import com.neurotoxin.steamclone.repository.connect.GameTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameTagService {

    private final GameTagRepository gameTagRepository;

    // Connect Tables
    @Transactional
    public GameTag create(Game game, Tag tag) {
        GameTag gameTag = new GameTag(game, tag);
        return gameTagRepository.save(gameTag);
    }

    // Searching All GameTag with Single Game
    public List<GameTag> getGameTags(Game game) {
        return gameTagRepository.findGameTagByGame(game);
    }

    // Delete Single Table
    @Transactional
    public void delete(GameTag gameTag) {
        gameTagRepository.delete(gameTag);
    }

    // Delete Connected All Table with Single Game
    @Transactional
    public void disconnect(Game game) {
        List<GameTag> connectedAllTags = getGameTags(game);
        for (GameTag gameTag : connectedAllTags) {
            delete(gameTag);
        }
    }

}
