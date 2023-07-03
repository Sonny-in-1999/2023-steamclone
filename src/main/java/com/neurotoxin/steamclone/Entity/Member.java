package com.neurotoxin.steamclone.Entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "steam_id")
    private Steam steam;

    @OneToMany(mappedBy = "member")
    private List<Library> libraries;

    @OneToMany(mappedBy = "member")
    private List<WishListItem> wishListItems;

    @OneToMany(mappedBy = "member")
    private List<CartItem> cartItems;


    //==기타 메서드==//

}
