package com.neurotoxin.steamclone.entity.connect;

import com.neurotoxin.steamclone.entity.single.CartItem;
import com.neurotoxin.steamclone.entity.single.Game;
import com.neurotoxin.steamclone.entity.single.Member;
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
}