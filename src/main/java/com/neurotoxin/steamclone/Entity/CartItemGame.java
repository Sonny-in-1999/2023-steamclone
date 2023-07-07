package com.neurotoxin.steamclone.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class CartItemGame {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_item_id")
    private CartItem cartItem;

    public CartItemGame(Game game, CartItem cartItem) {
        this.game = game;
        this.cartItem = cartItem;
    }

    public CartItemGame(Member member, Game game, CartItem cartItem) {
        this.member = member;
        this.game = game;
        this.cartItem = cartItem;
    }
}