package com.neurotoxin.steamclone.Entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String loginName;

    private String password;

    private String phoneNumber;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "steam_id")
    private Steam steam;

    @OneToOne (mappedBy = "member")
    private Library library = new Library();

    @OneToMany(mappedBy = "member")
    private List<WishListItem> wishList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<CartItem> cart = new ArrayList<>();


    //==기타 메서드==//

}
