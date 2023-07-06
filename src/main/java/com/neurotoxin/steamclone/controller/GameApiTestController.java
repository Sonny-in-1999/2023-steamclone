package com.neurotoxin.steamclone.controller;

import com.neurotoxin.steamclone.Entity.Game;
import com.neurotoxin.steamclone.Entity.GameTag;
import com.neurotoxin.steamclone.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GameApiTestController {

    private final GameService gameService;

    @GetMapping("/api/test/game")
    public List<Game> gameList() {
        return gameService.findAllGames();
    }

    @GetMapping("/api/test/game/{gameId}")
    public Game findGameById(@PathVariable Long gameId) {
        return gameService.findGameById(gameId);
    }

    @GetMapping("/api/test/search/{gameName}")
    public Game findGameByName(@PathVariable String gameName) {
        return gameService.findGameByName(gameName);
    }

    @PostMapping("/api/test/game/add")
    public void addGame(@RequestBody Game devGame,String ... tagName ) {
        gameService.create(devGame, tagName);
    }

    @DeleteMapping("/api/test/game/{gameId}")
    public void deleteGame(@PathVariable Long gameId) {
        gameService.delete(gameId);
    }

    @PutMapping("/api/test/game/{gameId}")
    public void updateGame(@PathVariable Long gameId, @RequestBody Game game) {
        gameService.update(gameId, game);
    }
}
