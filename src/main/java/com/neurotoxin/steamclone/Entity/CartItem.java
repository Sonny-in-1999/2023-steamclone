package com.neurotoxin.steamclone.Entity;


import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
public class CartItem {

    @Id
    @GeneratedValue
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne
    @JoinTable(
            name = "member_cart_item",
            joinColumns = @JoinColumn(name = "cart_item_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private Member member;

    @ManyToMany
    @JoinTable(
            name = "cart_item_game",
            joinColumns = @JoinColumn(name = "cart_item_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    private List<Game> games;
}
