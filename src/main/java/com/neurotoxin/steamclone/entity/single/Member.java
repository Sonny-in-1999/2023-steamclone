package com.neurotoxin.steamclone.entity.single;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
