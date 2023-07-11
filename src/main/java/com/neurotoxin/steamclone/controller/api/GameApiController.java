package com.neurotoxin.steamclone.controller.api;

import com.neurotoxin.steamclone.entity.single.Game;
import com.neurotoxin.steamclone.service.single.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GameApiController {

    private final GameService gameService;

    @GetMapping("/gameList")
    public List<Game> gameList() {
        return gameService.findAllGames();
    }

    @GetMapping("/{gameId}")
    public Game findGameById(@PathVariable Long gameId) {
        return gameService.findGameById(gameId);
    }

    @GetMapping("/search/{gameName}")
    public Game findGameByName(@PathVariable String gameName) {
        return gameService.findGameByTitle(gameName);
    }

    @PostMapping("/api/products/add")
    public void addGame(@RequestBody Game devGame) {
        gameService.createGame(devGame);
    }

    @DeleteMapping("/{gameId}")
    public void deleteGame(@PathVariable Long gameId) {
        gameService.delete(gameId);
    }

//    @PutMapping("/{gameId}")
//    public void updateGame(@PathVariable Long gameId, @RequestBody List<Long> tagIds) {
//        gameService.updateGame(gameId, tagIds);
//    }
}
