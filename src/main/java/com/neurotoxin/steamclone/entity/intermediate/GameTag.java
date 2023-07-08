package com.neurotoxin.steamclone.entity;

import com.neurotoxin.steamclone.entity.single.Game;
import com.neurotoxin.steamclone.entity.single.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@RequiredArgsConstructor
public class GameTag {

    @Id
    @GeneratedValue
    @Column(name = "game_tag_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    // Connecting Table
    public GameTag(Game game, Tag tag) {
        this.game = game;
        this.tag = tag;
    }
}
