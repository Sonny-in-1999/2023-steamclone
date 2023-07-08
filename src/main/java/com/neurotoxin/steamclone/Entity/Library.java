package com.neurotoxin.steamclone.Entity;


import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Library {


    @Id
    @GeneratedValue
    @Column(name = "library_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "library")
    private List<CartItem> purchasedGame = new ArrayList<>();

    // For connect Table Library - Member
    public void setMember(Member member) {
        this.member = member;
    }
}
