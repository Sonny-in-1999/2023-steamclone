package com.neurotoxin.steamclone.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String loginName;

    private String nickName;

    private String password;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)  // [USER, DEVELOPER]
    private Grade grade;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "steam_id")
    private Steam steam;

    @OneToOne(mappedBy = "member", cascade = PERSIST, fetch = LAZY)
    private Library library = new Library();

    @OneToMany(mappedBy = "member")
    private List<WishListItem> wishList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<CartItem> cart = new ArrayList<>();
}
