package com.neurotoxin.steamclone.entity.single;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Wallet {

    @Id
    @GeneratedValue
    @Column(name = "wallet_id")
    private Long id;

    private int balance;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public void setMember(Member member) {
        this.member = member;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Wallet(int balance) {
        this.balance = balance;
    }
}
