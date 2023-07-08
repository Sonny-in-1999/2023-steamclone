package com.neurotoxin.steamclone.Entity;


import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Tag {

    @Id
    @GeneratedValue
    @Column(name = "tag_id")
    private Long id;

    private String name;

    public void setName(String name) {
        this.name = name;
    }

}
