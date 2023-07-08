package com.neurotoxin.steamclone.entity.single;


import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
public class Steam {

    @Id
    @GeneratedValue
    @Column(name = "steam_id")
    private Long id;

    // 내용 차후 추가

    @OneToMany(mappedBy = "steam")
    private List<Member> members;

    //==기타 메서드==//
}
