package com.neurotoxin.steamclone.service.single;

import com.neurotoxin.steamclone.entity.single.Franchise;
import com.neurotoxin.steamclone.entity.single.Game;
import com.neurotoxin.steamclone.repository.single.FranchiseRepository;
import com.neurotoxin.steamclone.repository.single.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FranchiseService {

    private final FranchiseRepository franchiseRepository;
    private final GameRepository gameRepository;

    @Transactional
    public Franchise create(Franchise franchise) {
        return franchiseRepository.save(franchise);
    }

    public Franchise findFranchiseById(Long franchiseId) {
        return franchiseRepository.findFranchiseById(franchiseId);
    }

    public Franchise findFranchiseByNameContaining(String name) {
        return franchiseRepository.findFranchiseByNameContaining(name);
    }

    @Transactional
    public void delete(Franchise franchise) {
        franchiseRepository.delete(franchise);
    }

    @Transactional
    public void register(Franchise franchise, List<Long> gameIds) {
        Franchise findFranchise = franchiseRepository.findFranchiseById(franchise.getId());

        if (findFranchise != null) {
            List<Game> findGames = findFranchise.getGames();

            for (Long gameId : gameIds) {
                findGames.add(gameRepository.findGameById(gameId));
            }
            if (findGames != null && !findGames.isEmpty()) {
                create(franchise);
                franchise.setGames(findGames);
            } else {
                throw new NullPointerException("프랜차이즈 게임이 없습니다.");
            }
        }   else {
            throw new NullPointerException("프랜차이즈가 없습니다.");
        }
    }
    // 프랜차이즈에서 게임을 삭제
    @Transactional
    public void disconnectGame(Game game) {
        List<Game> franchiseGames = game.getFranchise().getGames();

        int findIndex = franchiseGames.indexOf(game);
        if (findIndex != -1) {
                franchiseGames.remove(findIndex);
        }
    }
    // 프랜차이즈에 연결된 모든 게임과의 연결 삭제
    @Transactional
    public void disconnectAll(Franchise franchise) {
        List<Game> games = franchise.getGames();
        for (Game game : games) {
            game.setFranchise(null);                // 프랜차이즈 내 게임들에 등록된 프랜차이즈 정보를 모두 제거합니다.
        }

        franchise.getGames().clear();               // 프랜차이즈에 저장된 게임들을 모두 제거합니다.
    }
}