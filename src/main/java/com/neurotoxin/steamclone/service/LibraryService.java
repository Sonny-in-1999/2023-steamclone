package com.neurotoxin.steamclone.service;

import com.neurotoxin.steamclone.Entity.Game;
import com.neurotoxin.steamclone.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryService {

    private final LibraryRepository libraryRepository;

    // 구매한 게임 등록
    public Game create(Game purchasedGame) {
        return libraryRepository.save(purchasedGame);
    }

    // 라이브러리 게임 전건 조회
    public List<Game> findAllPurchasedGames(Game purchasedGame) {
        return libraryRepository.findAll();
    }

    // 라이브러리 게임 단일 조회
    public Game findPurchasedGameById(Long purchasedGameId) {
        return libraryRepository.findPurchasedItemById(purchasedGameId);
    }

    // 라이브러리 게임 삭제
    public void delete(Game purchasedGame) {
        libraryRepository.delete(purchasedGame);
    }
}
