package com.neurotoxin.steamclone.controller.view;


import com.neurotoxin.steamclone.entity.single.Game;
import com.neurotoxin.steamclone.entity.single.Tag;
import com.neurotoxin.steamclone.service.connect.GameTagService;
import com.neurotoxin.steamclone.service.single.GameService;
import com.neurotoxin.steamclone.service.single.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;
    private final TagService tagService;
    private final GameTagService gameTagService;


    // 게임 정보 페이지(상점 페이지)
    @GetMapping("/app/{gameId}")
    public String app(@PathVariable Long gameId,Model model) {
        Game game = gameService.findGameById(gameId);
        model.addAttribute("game", game);
        return "app";
    }

    // 게임 등록 페이지
    @GetMapping("/products/add")
    public String addGamePage(Model model) {
        List<Tag> tags = tagService.findAllTags(); // DB에 등록되어 있는 태그를 불러옴
        model.addAttribute("game", new Game());
        model.addAttribute("tags", tags);
        return "game_add";
    }

    // 게임 등록
    @PostMapping("/products/add")
    public String addGame(@ModelAttribute Game game, @RequestParam() List<Long> selectedTags) {
        Game createdGame = gameService.createGame(game);
        for (Long tagId : selectedTags) {
            Tag tag = tagService.findTagById(tagId);
            gameTagService.create(createdGame, tag);
        }
        return "redirect:/products/list";
    }

    // 게임 정보 수정 페이지(삭제도 여기서 가능)
    @GetMapping("/products/edit/{gameId}")
    public String editGamePage(@PathVariable Long gameId, Model model) {
        Game game = gameService.findGameById(gameId);
        List<Long> gameTagIds = game.getTags().stream()
                .map(gameTag -> gameTag.getTag().getId())
                .collect(Collectors.toList());
        if (game == null) {
            return "redirect:/products/list";
        }
        List<Tag> tags = tagService.findAllTags();
        model.addAttribute("game", game);
        model.addAttribute("tags", tags);
        model.addAttribute("gameTagIds", gameTagIds);
        return "game_edit";
    }

    @PostMapping("/products/edit/{gameId}")
    public String editGame(@PathVariable Long gameId, @ModelAttribute Game game, @RequestParam List<Long> tagIds) {
        gameService.updateGame(gameId, game, tagIds);
        return "redirect:/products/list";
    }

    @GetMapping("/products/delete/{gameId}")
    public String deleteGamePage(@PathVariable Long gameId, Model model) {
        Game game = gameService.findGameById(gameId);
        model.addAttribute("game", game);
        return "game_delete";
    }

    @PostMapping("/products/delete/{gameId}")
    public String deleteGame(@PathVariable Long gameId) {
        gameService.delete(gameId);
        return "redirect:/products/list";
    }

    // 게임 목록(전체)
    @GetMapping("/products/list")
    public String gameList(Model model) {
        List<Game> games = gameService.findAllGames();
        model.addAttribute("games", games);
        return "game_list";
    }

    // 게임 삭제

}
