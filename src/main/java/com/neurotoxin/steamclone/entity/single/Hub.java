package com.neurotoxin.steamclone.entity.single;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
public class Hub {

    @Id
    @GeneratedValue
    @Column(name = "hub_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToMany(mappedBy = "hub")
    private List<Community> communities;
}
