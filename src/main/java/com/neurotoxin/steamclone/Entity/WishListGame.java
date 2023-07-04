package com.neurotoxin.steamclone.Entity;

import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class WishListGame {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn (name = "wishlist_item_id")
    private WishListItem wishListItem;

    @ManyToOne(fetch = LAZY)
    @JoinColumn (name = "game_id")
    private Game game;

}
