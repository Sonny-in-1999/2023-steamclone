package com.neurotoxin.steamclone.Entity;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@Entity
@Getter
@NoArgsConstructor
public class Game {

    @Id
    @GeneratedValue
    @Column(name = "game_id")
    private Long id;

    private String name;

    private int price;

    @OneToMany (mappedBy = "game", fetch = EAGER)
    private List<GameTag> tags = new ArrayList<>();

    @OneToMany (mappedBy = "game")
    private List<WishListGame> wishList = new ArrayList<>();

    @OneToMany (mappedBy = "game")
    private List<CartItemGame> cart = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
