package com.neurotoxin.steamclone.entity.single;


import com.neurotoxin.steamclone.entity.connect.CartItemGame;
import lombok.Getter;

import javax.persistence.*;
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

}
