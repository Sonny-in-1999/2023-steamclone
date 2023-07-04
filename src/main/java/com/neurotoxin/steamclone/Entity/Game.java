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

    @OneToMany (mappedBy = "game")
    private List<GameTag> tags = new ArrayList<>();

    @OneToMany (mappedBy = "game")
    private List<WishListGame> wishList = new ArrayList<>();

    @OneToMany (mappedBy = "game")
    private List<CartItemGame> cart = new ArrayList<>();
}
