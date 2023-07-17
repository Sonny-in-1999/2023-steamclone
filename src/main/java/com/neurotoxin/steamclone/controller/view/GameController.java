package com.neurotoxin.steamclone.controller.view;


import com.neurotoxin.steamclone.entity.single.Game;
import com.neurotoxin.steamclone.entity.single.Media;
import com.neurotoxin.steamclone.entity.single.Tag;
import com.neurotoxin.steamclone.service.single.GameService;
import com.neurotoxin.steamclone.service.single.MediaService;
import com.neurotoxin.steamclone.service.single.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;
    private final TagService tagService;
    private final MediaService mediaService;


    // 게임 정보 페이지(상점 페이지)
    @GetMapping("/app/{gameId}")
    public String app(@PathVariable Long gameId, Model model) {
        Game game = gameService.findGameById(gameId);
        model.addAttribute("game", game);
        return "game/app";
    }

    // 게임 등록 페이지
    @GetMapping("/products/add")
    public String addGamePage(Model model) {
        List<Tag> tags = tagService.findAllTags(); // DB에 등록되어 있는 태그를 불러옴
        model.addAttribute("game", new Game());
        model.addAttribute("tags", tags);
        return "game/game_add";
    }

    // 게임 등록
    @PostMapping("/products/add")
    public String addGame(@ModelAttribute Game game, @RequestParam("releaseDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate releaseDate, List<Long> selectedTags, List<MultipartFile> files) throws IOException {
        List<Media> media = mediaService.storeFiles(files, game);
        game.setReleaseDate(releaseDate);
        gameService.addGame(game, selectedTags, media);
        return "redirect:/products/list";
    }

    // 게임 정보 수정 페이지(여기서 삭제 페이지로 이동 가능)
    @GetMapping("/products/edit/{gameId}")
    public String editGamePage(@PathVariable Long gameId, Model model) {
        Game game = gameService.findGameById(gameId);
        if (game == null) {
            return "redirect:/products/list";
        }

        List<Long> gameTagIds = game.getTags().stream()
                .map(gameTag -> gameTag.getTag().getId())
                .collect(Collectors.toList());

        List<Tag> tags = tagService.findAllTags();
        model.addAttribute("game", game);
        model.addAttribute("tags", tags);
        model.addAttribute("gameTagIds", gameTagIds);
        return "game/game_edit";
    }

    // 게임 정보 수정
    @PostMapping("/products/edit/{gameId}")
    public String editGame(@PathVariable Long gameId, @ModelAttribute Game game, @RequestParam(required = false) List<Long> tagIds, List<MultipartFile> files) throws IOException {
        if (!files.isEmpty()) {
            List<Media> media = mediaService.storeFiles(files, game);
            gameService.updateGame(gameId, game, tagIds, media);
        } else {
            gameService.updateGame(gameId, game, tagIds, game.getMedia());
        }

        return "redirect:/products/list";
    }

    // 게임 삭제 페이지
    @GetMapping("/products/delete/{gameId}")
    public String deleteGamePage(@PathVariable Long gameId, Model model) {
        Game game = gameService.findGameById(gameId);
        model.addAttribute("game", game);
        return "game/game_delete";
    }

    // 게임 삭제
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
        return "game/game_list";
    }


}
