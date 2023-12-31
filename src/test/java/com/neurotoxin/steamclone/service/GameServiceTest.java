package com.neurotoxin.steamclone.service;

import com.neurotoxin.steamclone.Entity.*;
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
    @Autowired
    GameTagService gameTagService;
    @Autowired
    WishListGameService wishListGameService;
    @Autowired
    CartItemGameService cartItemGameService;

    @Test
    @DisplayName("Add Game")
    @Transactional
    public void addGame() throws Exception {
        // given
        Game game1 = new Game();
        Tag tag1 = new Tag();
        Tag tag2 = new Tag();
        tag1.setName("adult");
        tag2.setName("nude");
        tagService.create(tag1);
        tagService.create(tag2);
        game1.setName("NMH RPG");
        game1.setPrice(7);
        //when
        gameService.create(game1, "adult", "nude");

        List<GameTag> allTags = gameTagService.getGameTags(game1);
        //then
        assertThat(game1).isEqualTo(gameService.findGameById(game1.getId()));
        assertThat(allTags.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Delete Game")
    @Transactional
    public void deleteGame() throws Exception {
        // 게임 생성(태그 포함)
        Game game1 = new Game();
        game1.setName("NMH Adventure");
        Tag tag1 = new Tag();
        Tag tag2 = new Tag();
        tag1.setName("adult");
        tag2.setName("nude");
        tagService.create(tag1);
        tagService.create(tag2);
        gameService.create(game1, "adult", "nude");


        // 게임이 태그와 함께 성공적으로 DB에 저장
        List<Game> allGames = gameService.findAllGames();
        assertThat(allGames.size()).isEqualTo(1);
        List<GameTag> allTags = gameTagService.getGameTags(game1);
        assertThat(allTags.size()).isEqualTo(2);

        // DB에서 게임이 정상적으로 삭제
        gameService.delete(game1.getId());
        List<Game> allGamesAfDelete = gameService.findAllGames();
        assertThat(allGamesAfDelete.size()).isEqualTo(0);

        // 게임 삭제 후 중간테이블들의 fk가(중간테이블이 갖고 있는 해당 게임의 정보가) 정상적으로 삭제
        List<GameTag> gameTags = gameTagService.getGameTags(game1);
        List<WishListGame> wishListGames = wishListGameService.getWishListGames(game1);
        List<CartItemGame> cartItemGames = cartItemGameService.getCartItemGame(game1);
        assertThat(gameTags.size()).isEqualTo(0);
        assertThat(wishListGames.size()).isEqualTo(0);
        assertThat(cartItemGames.size()).isEqualTo(0);
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
