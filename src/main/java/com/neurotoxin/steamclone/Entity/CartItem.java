package com.neurotoxin.steamclone.Entity;


import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class CartItem extends WishListItem {

    @Id
    @GeneratedValue
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "library_id")
    private Library library;

    @OneToMany(mappedBy = "cartItem")
    private List<CartItemGame> cart = new ArrayList<>();
}
