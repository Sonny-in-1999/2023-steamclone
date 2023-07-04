package com.neurotoxin.steamclone.Entity;

import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class CartItemGame {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn (name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn (name = "game_id")
    private Game game;
}