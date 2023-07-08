package com.neurotoxin.steamclone.Entity;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue
    @Column(name = "cart_item_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "library_id")
    private Library library;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "cartItem")
    private List<CartItemGame> cart = new ArrayList<>();


    public CartItem(Member member, List<CartItemGame> cart) {
        this.member = member;
        this.cart = cart;
    }

    // Order Status
    public void setStatus(Status status) {
        this.status = status;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
