package com.neurotoxin.steamclone.entity.single;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import net.bytebuddy.asm.Advice;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
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

    private String role;

    @CreationTimestamp
    private LocalDate createdAt;

    @OneToOne(mappedBy = "member",cascade = PERSIST, fetch = LAZY)
    private Wallet wallet = new Wallet(0);

    @OneToOne(mappedBy = "member",cascade = PERSIST, fetch = LAZY)
    private Library library = new Library();

    @OneToMany(mappedBy = "member")
    private List<WishListItem> wishList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<CartItem> cart = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Community> communities;

    @OneToMany(mappedBy = "member")
    private List<Comment> comments;

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

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
