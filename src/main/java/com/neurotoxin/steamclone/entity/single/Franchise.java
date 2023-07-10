package com.neurotoxin.steamclone.entity.single;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
public class Franchise {

    @Id
    @GeneratedValue
    @Column(name = "franchise_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "franchise")
    private List<Game> games;

    public void setName(String name) {
        this.name = name;
    }
}
