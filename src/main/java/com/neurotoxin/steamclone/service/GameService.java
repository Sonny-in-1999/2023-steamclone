package com.neurotoxin.steamclone.service;


import com.neurotoxin.steamclone.Entity.Game;
import com.neurotoxin.steamclone.Entity.Tag;
import com.neurotoxin.steamclone.repository.GameRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class GameService {

    private final GameRepository gameRepository;
    private final TagService tagService;
    private final GameTagService gameTagService;

    public GameService(GameRepository gameRepository, TagService tagService, GameTagService gameTagService) {
        this.gameRepository = gameRepository;
        this.tagService = tagService;
        this.gameTagService = gameTagService;
    }

    // 게임 등록 메소드
    @Transactional
    public Game create(Game game, String ... tagName){
        validateDupGame(game);

        List<Tag> findTags = new ArrayList<>();
        // 생성자 주입을 통해 받은 리스트에서, 생성자 주입을 통해 GameTag 인스턴스를 형성합니다.
        for (String s : tagName) {            // PathVariables를 통해 String[]를 받은 경우, 하나씩 찾습니다
            findTags.add(tagService.findByName(s));
        }
        if (findTags.isEmpty()) {
            throw new NullPointerException("해당하는 태그가 없습니다.");
        } else {
            for (Tag tag : findTags) {
                gameTagService.create(game, tag);       // 검색하여 나온 태그를 통해 게임과 태그 간의 관계를 형성할 인스턴스를 만들고 그걸 주입 받은 생성자를 통해 저장합니다.
            }
            return gameRepository.save(game);
        }
    }

    // 게임 전건 조회
    public List<Game> findAllGames() {
        return gameRepository.findAll();
    }

    // 게임 단일 조회 (Id 이용)
    public Game findGameById(Long gameId) {
        return gameRepository.findGameById(gameId);
    }

    // 게임 단일 조회 (Name 이용)
    public Game findGameByName(String gameName) {
        return gameRepository.findGameByName(gameName);
    }

    // 게임 삭제
    @Transactional
    public void delete(Long gameId) {
        Game findGame = gameRepository.findGameById(gameId);
        validateGame(findGame);

        // 중간 테이블에서 연결된 GameTag를 전부 삭제합니다.
        gameTagService.disconnect(findGame);
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
        Game findGame = gameRepository.findGameByName(givenGame.getName());
        if (findGame == null) {
            throw new NullPointerException("존재하지 않는 게임입니다.");
        }
    }

    private void validateDupGame(Game givenGame) {
        Game findGame = gameRepository.findGameByName(givenGame.getName());
        if (findGame != null) {
            throw new IllegalStateException("이미 등록된 게임입니다.");
        }
    }
}
