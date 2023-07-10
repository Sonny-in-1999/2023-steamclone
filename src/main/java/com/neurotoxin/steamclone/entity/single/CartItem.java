package com.neurotoxin.steamclone.entity.single;


import com.neurotoxin.steamclone.entity.connect.CartItemGame;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
public class CartItem {

    @Id
    @GeneratedValue
    @Column(name = "cart_item_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate purchaseDate;

    private LocalDate lastPlayed;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "library_id")
    private Library library;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "cartItem")
    private List<CartItemGame> cart = new ArrayList<>();

    public void setMember(Member member) {
        this.member = member;
    }

    // Order Status
    public void setStatus(Status status) {
        this.status = status;
    }
    // Purchase Date
    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
    // Last Played Date
    public void setLastPlayed(LocalDate lastPlayed) {
        this.lastPlayed = lastPlayed;
    }
}