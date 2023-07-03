package com.neurotoxin.steamclone.Entity;


import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
public class Game {

    @Id
    @GeneratedValue
    @Column(name = "game_id")
    private Long id;

    private String name;

    private int price;

    @ManyToMany
    @JoinTable(
            name = "game_tag",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "tag")
    )
    private List<Tag> tags;

    @ManyToMany(mappedBy = "games")
    private List<Library> libraries;

    @ManyToMany(mappedBy = "games")
    private List<WishListItem> wishListItems;

    @ManyToMany(mappedBy = "games")
    private List<CartItem> cartItems;


}
