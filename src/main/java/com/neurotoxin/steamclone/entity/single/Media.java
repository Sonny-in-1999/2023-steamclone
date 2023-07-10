package com.neurotoxin.steamclone.entity.single;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Media {

    @Id
    @GeneratedValue
    @Column(name = "media_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private MediaType mediaType;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }
}