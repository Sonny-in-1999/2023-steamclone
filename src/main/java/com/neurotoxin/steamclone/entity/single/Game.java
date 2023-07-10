package com.neurotoxin.steamclone.entity.single;


import com.neurotoxin.steamclone.entity.connect.CartItemGame;
import com.neurotoxin.steamclone.entity.connect.GameTag;
import com.neurotoxin.steamclone.entity.connect.WishListGame;
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

    private String title;

    private int price;

    private String description;

    @OneToMany (mappedBy = "game", fetch = EAGER)
    private List<GameTag> tags = new ArrayList<>();

    @OneToMany (mappedBy = "game")
    private List<WishListGame> wishList = new ArrayList<>();

    @OneToMany (mappedBy = "game")
    private List<CartItemGame> cart = new ArrayList<>();

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
