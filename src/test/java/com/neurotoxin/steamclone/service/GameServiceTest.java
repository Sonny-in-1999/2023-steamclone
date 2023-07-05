package com.neurotoxin.steamclone.service;

import com.neurotoxin.steamclone.Entity.Game;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class GameServiceTest {

    @Autowired
    GameService gameService;

    @Test
    @DisplayName("게임 생성")
    @Transactional
    public void addGame() throws Exception {
        // given
        Game game1 = new Game();

        game1.setName("sexsex1");
        game1.setPrice(6974);

    }




}
