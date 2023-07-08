package com.neurotoxin.steamclone.Entity;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class WishListItem {

    @Id
    @GeneratedValue
    @Column(name = "wishlist_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn (name = "member_id")
    private Member member;

    @OneToMany (mappedBy = "wishListItem")
    private List<WishListGame> wishList = new ArrayList<>();

    public WishListItem(Member member, List<WishListGame> wishList) {
        this.member = member;
        this.wishList = wishList;
    }
}
