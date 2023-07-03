package com.neurotoxin.steamclone.Entity;


import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
public class WishListItem {


    @Id
    @GeneratedValue
    @Column(name = "wishlist_item_id")
    private Long id;

    @ManyToOne
    @JoinTable(
            name = "member_wish_list",
            joinColumns = @JoinColumn(name = "wishlist_item_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private Member member;

    @ManyToMany
    @JoinTable(
            name = "wishlist_item_game",
            joinColumns = @JoinColumn(name = "wishlist_item_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    private List<Game> games;

}
