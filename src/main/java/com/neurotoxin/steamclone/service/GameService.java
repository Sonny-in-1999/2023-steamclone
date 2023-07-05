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
    public Game create(Game game, GameTag gameTag){
        game.getTags().add(gameTag);
        validateDupGame(game);
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

    // 게임 검색
    public List<Game> findGameByName(String gameName) {
        return gameRepository.findGameByName(gameName);
    }

    // 게임 삭제
    @Transactional
    public void delete(Long gameId) {
        Game findGame = gameRepository.findGameById(gameId);
        validateGame(findGame);
        gameRepository.delete(findGame);
    }

    // 게임 정보 수정
    @Transactional
    public void update(Long gameId, Game newGame) {
        Game oldGame = gameRepository.findGameById(gameId);

        oldGame.setName(newGame.getName());
        oldGame.setPrice(newGame.getPrice());
        oldGame.setTags(newGame.getTags());

        gameRepository.save(oldGame);
    }

    // 중복, NULL 예외
    private void validateGame(Game givenGame) {
        Game findGame = gameRepository.findGameById(givenGame.getId());
        if (findGame == null) {
            throw new IllegalStateException("존재하지 않는 게임입니다.");
        }
    }

    private void validateDupGame(Game givenGame) {
        Game findGame = gameRepository.findGameById(givenGame.getId());
        if (findGame != null) {
            throw new NullPointerException("이미 등록된 게임입니다.");
        }
    }

}
