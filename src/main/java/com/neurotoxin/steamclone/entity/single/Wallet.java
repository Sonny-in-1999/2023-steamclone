package com.neurotoxin.steamclone.entity.single;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Wallet {

    @Id
    @GeneratedValue
    @Column(name = "wallet_id")
    private Long id;

    private Long balance;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public void setMember(Member member) {
        this.member = member;
    }
}
