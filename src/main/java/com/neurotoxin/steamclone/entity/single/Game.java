package com.neurotoxin.steamclone.entity.single;


import com.neurotoxin.steamclone.entity.connect.CartItemGame;
import com.neurotoxin.steamclone.entity.connect.GameTag;
import com.neurotoxin.steamclone.entity.connect.WishListGame;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

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

    private LocalDate postDate;

    @OneToMany (mappedBy = "game", fetch = EAGER)
    private List<GameTag> tags = new ArrayList<>();

    @OneToMany (mappedBy = "game")
    private List<WishListGame> wishList = new ArrayList<>();

    @OneToMany (mappedBy = "game")
    private List<CartItemGame> cart = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "franchise_id")
    private Franchise franchise;

    @OneToMany (mappedBy = "game")
    private List<Comment> comments;

    @OneToOne(mappedBy = "game")
    private Hub hub;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
    }

    public void setFranchise(Franchise franchise) {
        this.franchise = franchise;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setHub(Hub hub) {
        this.hub = hub;
    }
}
