package com.neurotoxin.steamclone.entity;

import com.neurotoxin.steamclone.entity.single.Game;
import com.neurotoxin.steamclone.entity.single.WishListItem;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class WishListGame {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn (name = "wishlist_item_id")
    private WishListItem wishListItem;

    @ManyToOne(fetch = LAZY)
    @JoinColumn (name = "game_id")
    private Game game;

    public WishListGame(Game game, WishListItem wishListItem) {
        this.game = game;
        this.wishListItem = wishListItem;
    }
}
