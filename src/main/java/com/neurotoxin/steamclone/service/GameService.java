package com.neurotoxin.steamclone.service;


import com.neurotoxin.steamclone.Entity.Game;
import com.neurotoxin.steamclone.Entity.GameTag;
import com.neurotoxin.steamclone.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameService {

    private final GameRepository gameRepository;

    // 게임 등록 메소드
    @Transactional
    public Game create(Game game, GameTag tags){
        game.getTags().add(tags);
        return gameRepository.save(game);
    }

    // 게임 전건 조회
    public List<Game> findAllGames() {
        return gameRepository.findAll();
    }

    // 게임 단일 조회 (Id 이용)
    public Game findGameById(Long gameId) {
        return gameRepository.findGameById(gameId);
    }

    // 게임 삭제
    @Transactional
    public void delete(Game game) {
        gameRepository.delete(game);
    }

    // 게임 정보 수정
    @Transactional
    public void update(Game game) {
    }
}
