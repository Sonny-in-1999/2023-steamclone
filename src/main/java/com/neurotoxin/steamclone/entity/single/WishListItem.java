package com.neurotoxin.steamclone.entity.single;


import com.neurotoxin.steamclone.entity.connect.WishListGame;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
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

    private LocalDate wishDate;

    public void setMember(Member member) {
        this.member = member;
    }

    public void setWishDate(LocalDate wishDate) {
        this.wishDate = wishDate;
    }
}
