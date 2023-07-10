package com.neurotoxin.steamclone.service.single;


import com.neurotoxin.steamclone.entity.connect.GameTag;
import com.neurotoxin.steamclone.entity.single.Game;
import com.neurotoxin.steamclone.entity.single.Tag;
import com.neurotoxin.steamclone.repository.connect.GameTagRepository;
import com.neurotoxin.steamclone.repository.single.GameRepository;
import com.neurotoxin.steamclone.service.connect.CartItemGameService;
import com.neurotoxin.steamclone.service.connect.GameTagService;
import com.neurotoxin.steamclone.service.connect.WishListGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final TagService tagService;
    private final GameTagService gameTagService;
    private final GameTagRepository gameTagRepository;
    private final WishListGameService wishListGameService;
    private final CartItemGameService cartItemGameService;

    // 게임 등록

    // 임시
    @Transactional
    public Game createGame(Game game) {
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

    // 게임 단일 조회 (Name 이용)
    public Game findGameByTitle(String title) {
        return gameRepository.findGameByTitle(title);
    }

    // 게임 삭제
    @Transactional
    public void delete(Long gameId) {
        Game findGame = gameRepository.findGameById(gameId);
        validateGame(findGame);

        // Game 테이블과 연결된 중간테이블들의 fk 삭제
        gameTagService.disconnect(findGame);
        wishListGameService.disconnect(findGame);
        cartItemGameService.disconnect(findGame);

        gameRepository.delete(findGame);
    }

    // 게임 정보 수정
    @Transactional
    public void updateGame(Long gameId, Game updatedGame, List<Long> tagIds) {
        Game game = gameRepository.findGameById(gameId);
        if (game == null) {
            throw new NullPointerException("Game not found");
        }

        game.setTitle(updatedGame.getTitle());
        game.setPrice(updatedGame.getPrice());
        game.setDescription(updatedGame.getDescription());

        // 기존의 GameTag 삭제
        game.getTags().clear();
        gameTagService.disconnect(game);

        // 새로운 GameTag 생성
        for (Long tagId : tagIds) {
            Tag tag = tagService.findTagById(tagId);
            if (tag != null) {
                GameTag gameTag = gameTagService.create(game, tag);
                game.getTags().add(gameTag);
            }
        }
    }

    // 중복, NULL 예외
    private void validateGame(Game givenGame) {
        Game findGame = gameRepository.findGameByTitle(givenGame.getTitle());
        if (findGame == null) {
            throw new NullPointerException("존재하지 않는 게임입니다.");
        }
    }

    private void validateDupGame(Game givenGame) {
        Game findGame = gameRepository.findGameByTitle(givenGame.getTitle());
        if (findGame != null) {
            throw new IllegalStateException("이미 등록된 게임입니다.");
        }
    }
}
