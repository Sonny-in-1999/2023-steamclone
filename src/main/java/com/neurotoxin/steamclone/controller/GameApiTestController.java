package com.neurotoxin.steamclone.controller;

import com.neurotoxin.steamclone.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GameApiTestController {

    private final GameService gameService;
}
