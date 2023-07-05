package com.neurotoxin.steamclone.service;

import com.neurotoxin.steamclone.Entity.Game;
import com.neurotoxin.steamclone.Entity.GameTag;
import com.neurotoxin.steamclone.Entity.Tag;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class GameServiceTest {

    @Autowired
    GameService gameService;

    @Autowired
    TagService tagService;

    @Test
    @DisplayName("Add Game")
    @Transactional
    public void addGame() throws Exception {
        // given
        Game game1 = new Game();
        GameTag gameTag = new GameTag();
        Tag tag1 = new Tag();
        tag1.setName("adult");
        tagService.create(tag1);
        game1.setName("NMH RPG");
        game1.setPrice(7);
        //when
        gameService.create(game1, "adult");
        //then
        assertThat(game1).isEqualTo(gameService.findGameById(game1.getId()));
    }

    @Test
    @DisplayName("Delete Game")
    @Transactional
    public void deleteGame() throws Exception {
        //given
        Game game1 = new Game();
        game1.setName("NMH Adventure");
        Tag tag1 = new Tag();
        Tag tag2 = new Tag();
        tag1.setName("adult");
        tag2.setName("nude");
        tagService.create(tag1);
        tagService.create(tag2);

        gameService.create(game1, "adult", "nude");
        List<Game> allGames = gameService.findAllGames();
        assertThat(allGames.size()).isEqualTo(1);
        //when
        gameService.delete(game1.getId());
        List<Game> allGamesAfDelete = gameService.findAllGames();
        //then
        assertThat(allGamesAfDelete.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Validate Duplicate")
    @Transactional
    public void validateDupGame() throws Exception {
        //given
        Game game1 = new Game();
        Game game2 = new Game();
        Tag tag1 = new Tag();

        tag1.setName("adult");
        game1.setName("NMH RPG");
        game2.setName("NMH RPG");

        tagService.create(tag1);
        gameService.create(game1, "adult");
        //when
        //then
        assertThrows(IllegalStateException.class, () -> gameService.create(game2, "ad"));
    }

    @Test
    @DisplayName("Validate NULL")
    @Transactional
    public void unknownGame() throws Exception {
        //given
        Game game1 = new Game();
        game1.setName("NMH Blood");

        assertThrows(NullPointerException.class, () -> gameService.delete(999L));
    }

}
